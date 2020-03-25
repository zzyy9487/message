package com.example.fakebook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.fakebook.register.RegisterBody
import com.example.fakebook.register.RegisterData
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {

    lateinit var apiInterface: APIInterface
    lateinit var registerBody: RegisterBody

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://34.80.201.121/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiInterface = retrofit.create(APIInterface::class.java)

        register_btn_ok.setOnClickListener {
            if (register_edit_name.text.toString().isEmpty() ||
                register_edit_password.text.isEmpty()){
                Toast.makeText(this@RegisterActivity, "帳號密碼都要填", Toast.LENGTH_SHORT).show()
            } else {

                if (register_edit_password.text.length < 4 ||
                    register_edit_password.text.length > 20){
                    Toast.makeText(this@RegisterActivity, "密碼介於4~20", Toast.LENGTH_SHORT).show()
                } else {
                    registerBody = RegisterBody(
                        register_edit_name.text.toString(),
                        register_edit_password.text.toString()
                    )
                    apiInterface.register(registerBody).enqueue(object :retrofit2.Callback<RegisterData>{
                        override fun onFailure(call: Call<RegisterData>, t: Throwable) {
                            Toast.makeText(this@RegisterActivity, t.toString(), Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<RegisterData>,
                            response: Response<RegisterData>
                        ) {
                            if (response.isSuccessful){
                                val data = response.body()
                                if (data?.api_token != null){
                                    Toast.makeText(this@RegisterActivity, "註冊成功！！！", Toast.LENGTH_SHORT).show()
                                    this@RegisterActivity.finish()
                                }
                            } else {

                            }
                        }
                    })
                }
            }

        }

        register_btn_back.setOnClickListener {
            this.finish()
        }
    }
}
