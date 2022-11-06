package com.example.veggie_table

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NetworkServiceUsers {
    @POST("register")
    fun register(
        @Body user: RegisterReq,
    ): Call<RegisterRes>

    @POST("login")
    fun login(
        @Body user: LoginReq,
    ): Call<LoginRes>

    @GET("{userID}")
    fun userInfo(
        @Path("userID") userID: String,
    ): Call<UserInfoRes>
}

data class RegisterReq(val id: String, val usermail: String, val password: String, val residence: String)
data class RegisterRes(val success: String)

data class LoginReq(val usermail: String, val password: String)
data class LoginRes(val loginSuccess: String, val user: UserInfoRes)

data class UserInfoRes(val _id: String, val id: String, val usermail: String, val residence: String)