package com.example.fakebook

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


}