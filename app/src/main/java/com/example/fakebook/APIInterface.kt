package com.example.fakebook

import com.example.fakebook.addgood.AddGoodBody
import com.example.fakebook.addgood.AddGoodData
import com.example.fakebook.addmsg0.AddMsg0Body
import com.example.fakebook.addmsg0.AddMsg0Data
import com.example.fakebook.login.LoginBody
import com.example.fakebook.login.LoginData
import com.example.fakebook.msg.Msg
import com.example.fakebook.register.RegisterBody
import com.example.fakebook.register.RegisterData
import com.example.fakebook.removegood.RemoveGoodBody
import com.example.fakebook.removegood.RemoveGoodData
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {
    @Headers("Content-Type: application/json","Accept: application/json")

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

    @POST("api/storePost")
    fun sendMSG0(
        @Header("Authorization") token: String,
        @Body body: AddMsg0Body
    ): Call<AddMsg0Data>

}