package com.example.notevault.data.network

import com.example.notevault.data.model.user.LoginResponse
import com.example.notevault.data.model.user.User
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST

private const val BASE_URL = "Your API"
private val json = Json {
    ignoreUnknownKeys = true  // prevents crash if backend sends extra fields
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface UserApiService {

    @POST("public/login")
    suspend fun loginUser(@Body user: User): Response<LoginResponse>

    @POST("public/signup")
    suspend fun signup(@Body user: User): Response<Unit>
}

object UserApi {
    val retrofitService : UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }
}