package com.example.fakebook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fakebook.msg.Msg
import com.example.fakebook.msg.MsgAdapter
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MsgActivity : AppCompatActivity() {

    var userName: String = ""
    var userId: String = ""
    var userToken: String = ""
    lateinit var apiInterface: APIInterface
    lateinit var recyclerView: RecyclerView
    var adapter = MsgAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_msg)

        userName = intent?.getStringExtra("name") ?: return
        userId = intent?.getStringExtra("id") ?: return
        userToken = intent?.getStringExtra("api_token") ?: return
        adapter.updateUserId(userId.toInt())

        val retrofit = Retrofit.Builder()
            .baseUrl("http://34.80.201.121/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiInterface = retrofit.create(APIInterface::class.java)
        apiInterface.getBoard().enqueue(object :retrofit2.Callback<MutableList<Msg>>{
            override fun onFailure(call: Call<MutableList<Msg>>, t: Throwable) {
                Toast.makeText(this@MsgActivity, t.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<MutableList<Msg>>,
                response: Response<MutableList<Msg>>
            ) {
                if (response.isSuccessful){
                    val data = response.body()
                    if (data != null){
                        adapter.updateMSG(data)
                        recyclerView = findViewById(R.id.recyclerView_Msg)
                        recyclerView.layoutManager = LinearLayoutManager(this@MsgActivity)
                        recyclerView.adapter = adapter
                    }
                }
            }
        })


    }
}
