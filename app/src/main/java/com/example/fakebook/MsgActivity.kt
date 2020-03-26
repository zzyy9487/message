package com.example.fakebook

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.fakebook.fragment.Msg0Fragment
import com.example.fakebook.fragment.Msg1Fragment
import com.example.fakebook.fragment.Msg2Fragment
import com.example.fakebook.msg.Msg
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MsgActivity : AppCompatActivity() {

    var userName: String = ""
    var userId: String = ""
    var userToken: String = ""
    var bearerToken: String = ""
    lateinit var retrofit: Retrofit
    lateinit var msgViewModel: MsgViewModel
    lateinit var apiInterface: APIInterface
    val msg0Fragment = Msg0Fragment()
    lateinit var msg1Fragment: Msg1Fragment
    lateinit var msg2Fragment: Msg2Fragment
    val manager = this.supportFragmentManager
    var msg0Number = 0
    var msg1Number = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_msg)

        userName = intent?.getStringExtra("name") ?: return
        userId = intent?.getStringExtra("id") ?: return
        userToken = intent?.getStringExtra("api_token") ?: return
        bearerToken = "Bearer "+ userToken

        msgViewModel = ViewModelProvider(this).get(MsgViewModel::class.java)
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragmentLayout, msg0Fragment).commit()

        retrofit = Retrofit.Builder()
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
                        msgViewModel.updateMsg0List(data)
                    }
                }
            }
        })
    }

    fun renewData() {
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
                        msgViewModel.updateMsg0List(data)
                    }
                }
            }
        })
    }
}
