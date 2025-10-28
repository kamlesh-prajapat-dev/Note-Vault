package com.example.notevault.data.model.note

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ObjectId(
    @SerialName("timestamp")
    val timestamp: Long = 0,

    @SerialName("date")
    val date: String = ""
)
