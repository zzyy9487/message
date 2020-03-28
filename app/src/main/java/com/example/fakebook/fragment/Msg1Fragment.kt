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
import com.example.fakebook.getmsg1.GetMsg1Body
import com.example.fakebook.getmsg1.Msg1
import com.example.fakebook.getmsg1.Msg1Adapter
import com.example.fakebook.getmsg2.GetMsg2Body
import com.example.fakebook.getmsg2.Msg2
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
    lateinit var getMsg1Body: GetMsg1Body
    lateinit var getMsg2Body: GetMsg2Body
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

        msg1Adapter.setclickedListener(object :Msg1Adapter.clickedListener{
            override fun showMSG2(msg1id: Int) {
                act.msg1Number = msg1id
                act.msg2Fragment = Msg2Fragment()
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
                val transactionmsg2 = act.manager.beginTransaction()
                transactionmsg2.replace(R.id.fragmentLayout, act.msg2Fragment)
                transactionmsg2.addToBackStack(null)
                transactionmsg2.commit()
            }
        })

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
                            getMsg1Body = GetMsg1Body(act.msg0Number)
                            act.apiInterface.getMSG1(getMsg1Body).enqueue(object :retrofit2.Callback<MutableList<Msg1>>{
                                override fun onFailure(call: Call<MutableList<Msg1>>, t: Throwable) {
                                    Toast.makeText(act, t.toString(), Toast.LENGTH_SHORT).show()
                                }

                                override fun onResponse(
                                    call: Call<MutableList<Msg1>>,
                                    response: Response<MutableList<Msg1>>
                                ) {
                                    if (response.isSuccessful){
                                        val data = response.body()
                                        if (data != null){
                                            act.msgViewModel.updateMsg1List(data)
                                        }
                                    }
                                }
                            })
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
