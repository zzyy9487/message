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
import com.example.fakebook.msg.Likes
import com.example.fakebook.msg.Msg
import com.example.fakebook.msg.User
import com.example.fakebook.say.Say
import kotlinx.android.synthetic.main.activity_msg.*
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
    var sayList = mutableListOf<Say>()

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
        renewData()

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
//                        msgViewModel.updateMsg0List(data)
                        sayList.clear()
                        val user = User(0,"")
                        val likes = mutableListOf<Likes>()
                        for (i in 0 until data.size){
                            if (data[i].comments.size == 0){
                                sayList.add(
                                    Say(data[i].id,
                                        data[i].content,
                                        data[i].user_id,
                                        data[i].created_at,
                                        data[i].likes_count,
                                        data[i].likes,
                                        data[i].comments_count,
                                        data[i].user,
                                        1
                                    )
                                )
                            } else {
                                sayList.add(
                                    Say(data[i].id,
                                        data[i].content,
                                        data[i].user_id,
                                        data[i].created_at,
                                        data[i].likes_count,
                                        data[i].likes,
                                        data[i].comments_count,
                                        data[i].user,
                                        1
                                    )
                                )
                                for (j in 0 until data[i].comments.size){
                                    if (data[i].comments[j].replies.size == 0){
                                        sayList.add(
                                            Say(data[i].comments[j].id,
                                                data[i].comments[j].content,
                                                data[i].comments[j].user_id,
                                                data[i].comments[j].created_at,
                                                data[i].id,
                                                likes,
                                                0,
                                                data[i].comments[j].user,
                                                2
                                            )
                                        )
                                    } else {
                                        sayList.add(
                                            Say(data[i].comments[j].id,
                                                data[i].comments[j].content,
                                                data[i].comments[j].user_id,
                                                data[i].comments[j].created_at,
                                                data[i].id,
                                                likes,
                                                0,
                                                data[i].comments[j].user,
                                                2
                                            )
                                        )
                                        for (k in data[i].comments[j].replies.size -1 downTo 0){
                                            sayList.add(
                                                Say(data[i].comments[j].replies[k].id,
                                                    data[i].comments[j].replies[k].content,
                                                    data[i].comments[j].replies[k].user_id,
                                                    data[i].comments[j].replies[k].created_at,
                                                    data[i].comments[j].id,
                                                    likes,
                                                    0,
                                                    user,
                                                    3
                                                )
                                            )
                                        }
                                    }
                                }
                            }

                        }
                        msgViewModel.updateSayList(sayList)
                    }
                }
            }
        })
    }
}
