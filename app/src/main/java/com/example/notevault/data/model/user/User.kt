package com.example.notevault.data.model.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName(value = "userName")
    val userName: String = "",

    @SerialName(value = "password")
    val password: String = ""
)
