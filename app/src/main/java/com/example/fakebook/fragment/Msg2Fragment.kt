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
import com.example.fakebook.addmsg1.AddMsg1Data
import com.example.fakebook.addmsg2.AddMsg2Body
import com.example.fakebook.getmsg1.Msg1
import com.example.fakebook.getmsg2.GetMsg2Body
import com.example.fakebook.getmsg2.Msg2
import com.example.fakebook.getmsg2.Msg2Adapter
import retrofit2.Call
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class Msg2Fragment : Fragment() {

    lateinit var rootView: View
    lateinit var recyclerView: RecyclerView
    lateinit var editMsg2: EditText
    lateinit var btn_back: ImageButton
    lateinit var msg2_send: ImageButton
    lateinit var addMsg2Body: AddMsg2Body
    lateinit var getMsg2Body: GetMsg2Body
    val msg2Adapter = Msg2Adapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_msg2, container, false)
        val act = activity as MsgActivity
        recyclerView = rootView.findViewById(R.id.recyclerView_msg2)
        editMsg2 = rootView.findViewById(R.id.edit_msg2)
        msg2_send = rootView.findViewById(R.id.msg2_send)
        btn_back = rootView.findViewById(R.id.msg2_back)
        recyclerView.layoutManager = LinearLayoutManager(act)
        recyclerView.adapter = msg2Adapter

        val msg2ListOberser = Observer<MutableList<Msg2>> { newList ->
            msg2Adapter.updateMSG2(newList)
        }
        act.msgViewModel.msg2List.observe(act, msg2ListOberser)

        msg2_send.setOnClickListener {
            if (!editMsg2.text.isEmpty()) {
                msg2_send.isClickable = false
                addMsg2Body = AddMsg2Body(act.userId.toInt(), act.msg1Number,editMsg2.text.toString())
                act.apiInterface.sendMSG2(act.bearerToken, addMsg2Body).enqueue(object :retrofit2.Callback<AddMsg1Data>{
                    override fun onFailure(call: Call<AddMsg1Data>, t: Throwable) {
                        Toast.makeText(act, t.toString(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<AddMsg1Data>,
                        response: Response<AddMsg1Data>
                    ) {
                        if (response.isSuccessful){
                            getMsg2Body = GetMsg2Body(act.msg1Number)
                            act.apiInterface.getMSG2(getMsg2Body).enqueue(object :retrofit2.Callback<MutableList<Msg2>>{
                                override fun onFailure(call: Call<MutableList<Msg2>>, t: Throwable) {
                                    Toast.makeText(act, t.toString(), Toast.LENGTH_SHORT).show()
                                }

                                override fun onResponse(
                                    call: Call<MutableList<Msg2>>,
                                    response: Response<MutableList<Msg2>>
                                ) {
                                    if (response.isSuccessful){
                                        val data = response.body()
                                        if (data != null){
                                            act.msgViewModel.updateMsg2List(data)
                                        }
                                    }
                                }
                            })
                            act.renewData()
                            editMsg2.setText("")
                        }
                    }
                })
            }
            msg2_send.isClickable = true
        }


        btn_back.setOnClickListener {
            act.onBackPressed()
        }

        return rootView
    }

}
