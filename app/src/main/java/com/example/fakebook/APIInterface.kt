package com.example.fakebook

import com.example.fakebook.addgood.AddGoodBody
import com.example.fakebook.addgood.AddGoodData
import com.example.fakebook.login.LoginBody
import com.example.fakebook.login.LoginData
import com.example.fakebook.msg.Msg
import com.example.fakebook.register.RegisterBody
import com.example.fakebook.register.RegisterData
import com.example.fakebook.removegood.RemoveGoodBody
import com.example.fakebook.removegood.RemoveGoodData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface APIInterface {
    @POST("api/signup")
    fun register(
        @Body body: RegisterBody
    ): Call<RegisterData>

    @POST("api/login")
    fun login(
        @Body body: LoginBody
    ): Call<LoginData>

    @GET("api/board")
    fun getBoard(

    ): Call<MutableList<Msg>>

    @POST("api/storeLike")
    fun addGood(
        @Header("Authorization") token: String,
        @Body body: AddGoodBody
    ): Call<AddGoodData>

    @POST("api/dislike")
    fun removeGood(
        @Header("Authorization") token: String,
        @Body body:RemoveGoodBody
    ): Call<RemoveGoodData>

}