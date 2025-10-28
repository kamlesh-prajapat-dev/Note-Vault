package com.example.notevault.data.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName(value = "token")
    val token: String = "", //JWT Authentication token
)
