package com.example.fakebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fakebook.login.LoginBody
import com.example.fakebook.login.LoginData
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    lateinit var apiInterface: APIInterface
    lateinit var loginBody: LoginBody

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://34.80.201.121/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiInterface = retrofit.create(APIInterface::class.java)

        btn_login.setOnClickListener {
            if (edit_name.text.toString().isEmpty() ||
                edit_password.text.isEmpty()){
                Toast.makeText(this@MainActivity, "帳號密碼都要填", Toast.LENGTH_SHORT).show()
            } else {

                if (edit_password.text.length < 4 ||
                    edit_password.text.length > 20){
                    Toast.makeText(this@MainActivity, "密碼介於4~20", Toast.LENGTH_SHORT).show()
                } else {
                    loginBody = LoginBody(
                        edit_name.text.toString(),
                        edit_password.text.toString()
                    )
                    apiInterface.login(loginBody).enqueue(object :retrofit2.Callback<LoginData>{
                        override fun onFailure(call: Call<LoginData>, t: Throwable) {
                            Toast.makeText(this@MainActivity, t.toString(), Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<LoginData>,
                            response: Response<LoginData>
                        ) {
                            if (response.isSuccessful){
                                val data = response.body()
                                Toast.makeText(this@MainActivity, "登入成功！！！", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@MainActivity, MsgActivity::class.java)
                                intent.putExtra("id", data?.id.toString())
                                intent.putExtra("name", data?.name)
                                intent.putExtra("api_token", data?.api_token)
                                startActivity(intent)
                            } else {

                            }
                        }
                    })
                }
            }
        }

        btn_register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
