package com.example.fakebook

import com.example.fakebook.addgood.AddGoodBody
import com.example.fakebook.addgood.AddGoodData
import com.example.fakebook.addmsg0.AddMsg0Body
import com.example.fakebook.addmsg0.AddMsg0Data
import com.example.fakebook.addmsg1.AddMsg1Body
import com.example.fakebook.addmsg1.AddMsg1Data
import com.example.fakebook.addmsg2.AddMsg2Body
import com.example.fakebook.getmsg1.GetMsg1Body
import com.example.fakebook.getmsg1.Msg1
import com.example.fakebook.getmsg2.GetMsg2Body
import com.example.fakebook.getmsg2.Msg2
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

    @POST("api/storeComment")
    fun sendMSG1(
        @Header("Authorization") token: String,
        @Body body: AddMsg1Body
    ): Call<AddMsg1Data>

    @POST("api/allComment")
    fun getMSG1(
        @Body body: GetMsg1Body
    ): Call<MutableList<Msg1>>

    @POST("api/storeReply")
    fun sendMSG2(
        @Header("Authorization") token: String,
        @Body body: AddMsg2Body
    ): Call<AddMsg1Data>

    @POST("api/allReply")
    fun getMSG2(
        @Body body: GetMsg2Body
    ): Call<MutableList<Msg2>>

}