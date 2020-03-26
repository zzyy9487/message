package com.example.fakebook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fakebook.addgood.AddGoodBody
import com.example.fakebook.addgood.AddGoodData
import com.example.fakebook.msg.Msg
import com.example.fakebook.msg.MsgAdapter
import com.example.fakebook.removegood.RemoveGoodBody
import com.example.fakebook.removegood.RemoveGoodData
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class MsgActivity : AppCompatActivity() {

    var userName: String = ""
    var userId: String = ""
    var userToken: String = ""
    var bearerToken: String = ""
    lateinit var apiInterface: APIInterface
    lateinit var recyclerView: RecyclerView
    var adapter = MsgAdapter()
    lateinit var addGoodBody: AddGoodBody
    lateinit var removeGoodBody: RemoveGoodBody

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_msg)

        userName = intent?.getStringExtra("name") ?: return
        userId = intent?.getStringExtra("id") ?: return
        userToken = intent?.getStringExtra("api_token") ?: return
        adapter.updateUserId(userId.toInt())
        bearerToken = "Bearer "+ userToken

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

        adapter.setclickedListener(object :MsgAdapter.clickedListener{
            override fun addGood(likeId: Int) {
                addGoodBody = AddGoodBody(userId.toInt(), likeId)
                apiInterface.addGood(bearerToken, addGoodBody).enqueue(object :retrofit2.Callback<AddGoodData>{
                    override fun onFailure(call: Call<AddGoodData>, t: Throwable) {
                        Toast.makeText(this@MsgActivity, t.toString(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<AddGoodData>,
                        response: Response<AddGoodData>
                    ) {
                        if (response.isSuccessful){
                            renewData()
                        }
                    }
                })

            }

            override fun delGood(likeId: Int) {
                val client = OkHttpClient()

                val body = FormBody.Builder()
                        .add("like_id", likeId.toString())
                        .build()

                val request = Request.Builder()
                    .url("http://34.80.201.121/api/dislike")
                    .addHeader("Authorization", "Bearer " + userToken)
                    .post(body)
                    .build()

                val call = client.newCall(request)
                call.enqueue(object :okhttp3.Callback{
                    override fun onFailure(call: okhttp3.Call, e: IOException) {
                        Toast.makeText(this@MsgActivity, e.toString(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                        if (response.isSuccessful){
                            renewData()
                        }
                    }
                })
//                removeGoodBody = RemoveGoodBody(likeId)
//                apiInterface.removeGood(bearerToken, removeGoodBody).enqueue(object :retrofit2.Callback<RemoveGoodData>{
//                    override fun onFailure(call: Call<RemoveGoodData>, t: Throwable) {
//                        Toast.makeText(this@MsgActivity, t.toString(), Toast.LENGTH_SHORT).show()
//                    }
//
//                    override fun onResponse(
//                        call: Call<RemoveGoodData>,
//                        response: Response<RemoveGoodData>
//                    ) {
//                        if (response.isSuccessful){
//                            renewData()
//                        }
//                    }
//                })

            }
        })


    }

    private fun renewData() {
        apiInterface.getBoard().enqueue(object : Callback<MutableList<Msg>> {
            override fun onFailure(call: Call<MutableList<Msg>>, t: Throwable) {
                Toast.makeText(this@MsgActivity, t.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(
                call: Call<MutableList<Msg>>,
                response: Response<MutableList<Msg>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        adapter.updateMSG(data)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }
}
