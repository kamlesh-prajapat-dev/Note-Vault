package com.example.notevault.data.repository

import com.example.notevault.data.model.user.User
import com.example.notevault.data.network.UserApiService

class UserRepository(private val api: UserApiService) {

    suspend fun loginUser(loginRequest: User) = api.loginUser(loginRequest)

    suspend fun signupUser(user: User) = api.signup(user)
}