package com.example.fakebook.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fakebook.MsgActivity
import com.example.fakebook.R
import com.example.fakebook.addmsg1.AddMsg1Body
import com.example.fakebook.addmsg1.AddMsg1Data
import com.example.fakebook.getmsg1.Msg1
import com.example.fakebook.getmsg1.Msg1Adapter
import retrofit2.Call
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class Msg1Fragment : Fragment() {

    lateinit var rootView: View
    lateinit var recyclerView: RecyclerView
    lateinit var editMsg1: EditText
    lateinit var btn_back: ImageButton
    lateinit var msg1_send: ImageButton
    lateinit var addMsg1Body: AddMsg1Body
    val msg1Adapter = Msg1Adapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_msg1, container, false)
        val act = activity as MsgActivity
        recyclerView = rootView.findViewById(R.id.recyclerView_msg1)
        editMsg1 = rootView.findViewById(R.id.edit_msg1)
        msg1_send = rootView.findViewById(R.id.msg1_send)
        btn_back = rootView.findViewById(R.id.msg1_back)
        recyclerView.layoutManager = LinearLayoutManager(act)
        recyclerView.adapter = msg1Adapter

        val msg1ListOberser = Observer<MutableList<Msg1>> { newList ->
            msg1Adapter.updateMSG1(newList)
        }
        act.msgViewModel.msg1List.observe(act, msg1ListOberser)

        msg1_send.setOnClickListener {
            if (!editMsg1.text.isEmpty()) {
                msg1_send.isClickable = false
                addMsg1Body = AddMsg1Body(act.userId.toInt(), act.msg0Number,editMsg1.text.toString())
                act.apiInterface.sendMSG1(act.bearerToken, addMsg1Body).enqueue(object :retrofit2.Callback<AddMsg1Data>{
                    override fun onFailure(call: Call<AddMsg1Data>, t: Throwable) {
                        Toast.makeText(act, t.toString(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<AddMsg1Data>,
                        response: Response<AddMsg1Data>
                    ) {
                        if (response.isSuccessful){
                            act.renewData()
                            editMsg1.setText("")
                        }
                    }
                })
            }
            msg1_send.isClickable = true
        }


        btn_back.setOnClickListener {
            act.onBackPressed()
        }

        return rootView
    }

}
