package com.example.notevault.data.model.note

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class NotesEntry(
    @SerialName(value = "id")
    val id: String = "",

    @SerialName(value = "title")
    val title: String = "",

    @SerialName("content")
    val content: String = "",

    @SerialName("date")
    @Contextual
    val date: LocalDateTime = LocalDateTime.now()
)
