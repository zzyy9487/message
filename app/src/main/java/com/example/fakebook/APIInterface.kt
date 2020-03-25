package com.example.fakebook

import com.example.fakebook.login.LoginBody
import com.example.fakebook.login.LoginData
import com.example.fakebook.register.RegisterBody
import com.example.fakebook.register.RegisterData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface APIInterface {
    @POST("api/signup")
    fun register(
        @Body body:RegisterBody
    ): Call<RegisterData>

    @POST("api/login")
    fun login(
        @Body body:LoginBody
    ): Call<LoginData>

}