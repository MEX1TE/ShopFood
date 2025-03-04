package com.example.shopfood.data

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

interface ApiService {
    @POST("register")
    suspend fun register(@Body user: User): Response<Map<String, Any>>

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<Map<String, Any>>

    @POST("order")
    suspend fun createOrder(@Body order: Order): Response<Map<String, Any>>
}

data class LoginRequest(val email: String, val password: String)