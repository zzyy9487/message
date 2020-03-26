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
import com.example.fakebook.addgood.AddGoodBody
import com.example.fakebook.addgood.AddGoodData
import com.example.fakebook.addmsg0.AddMsg0Body
import com.example.fakebook.addmsg0.AddMsg0Data
import com.example.fakebook.msg.Msg
import com.example.fakebook.msg.Msg0Adapter
import com.example.fakebook.removegood.RemoveGoodBody
import com.example.fakebook.removegood.RemoveGoodData
import retrofit2.Call
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class Msg0Fragment : Fragment() {

    lateinit var rootView: View
    lateinit var recyclerView: RecyclerView
    lateinit var editMsg0: EditText
    lateinit var msg0_send: ImageButton
    val msg0Adapter = Msg0Adapter()
    lateinit var addGoodBody: AddGoodBody
    lateinit var removeGoodBody: RemoveGoodBody
    lateinit var addMsg0Body: AddMsg0Body

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_msg0, container, false)
        val act = activity as MsgActivity
        msg0Adapter.updateUserId(act.userId.toInt())
        recyclerView = rootView.findViewById(R.id.recyclerView_msg0)
        editMsg0 = rootView.findViewById(R.id.edit_msg0)
        msg0_send = rootView.findViewById(R.id.msg0_send)
        recyclerView.layoutManager = LinearLayoutManager(act)
        recyclerView.adapter = msg0Adapter

        val msg0ListOberser = Observer<MutableList<Msg>> { newList ->
            msg0Adapter.updateMSG(newList)
        }
        act.msgViewModel.msg0List.observe(act, msg0ListOberser)

        msg0Adapter.setclickedListener(object : Msg0Adapter.clickedListener{
            override fun addGood(likeId: Int) {
                addGoodBody = AddGoodBody(act.userId.toInt(), likeId)
                act.apiInterface.addGood(act.bearerToken, addGoodBody).enqueue(object :retrofit2.Callback<AddGoodData>{
                    override fun onFailure(call: Call<AddGoodData>, t: Throwable) {
                        Toast.makeText(act, t.toString(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<AddGoodData>,
                        response: Response<AddGoodData>
                    ) {
                        if (response.isSuccessful){
                            act.renewData()
                        }
                    }
                })
            }

            override fun delGood(likeId: Int) {
                removeGoodBody = RemoveGoodBody(likeId)
                act.apiInterface.removeGood(act.bearerToken, removeGoodBody).enqueue(object :retrofit2.Callback<RemoveGoodData>{
                    override fun onFailure(call: Call<RemoveGoodData>, t: Throwable) {
                        Toast.makeText(act, t.toString(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<RemoveGoodData>,
                        response: Response<RemoveGoodData>
                    ) {
                        if (response.isSuccessful){
                            act.renewData()
                        }
                    }
                })
            }
        })

        msg0_send.setOnClickListener {
            if (!editMsg0.text.isEmpty()) {
                msg0_send.isClickable = false
                addMsg0Body = AddMsg0Body(act.userId.toInt(), editMsg0.text.toString())
                act.apiInterface.sendMSG0(act.bearerToken, addMsg0Body).enqueue(object :retrofit2.Callback<AddMsg0Data>{
                    override fun onFailure(call: Call<AddMsg0Data>, t: Throwable) {
                        Toast.makeText(act, t.toString(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<AddMsg0Data>,
                        response: Response<AddMsg0Data>
                    ) {
                        if (response.isSuccessful){
                            act.renewData()
                            editMsg0.setText("")
                        }
                    }
                })
            }
            msg0_send.isClickable = true
        }

        
        return rootView
    }

}
