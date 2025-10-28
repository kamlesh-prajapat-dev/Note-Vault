package com.example.notevault.data.model.note

import java.time.LocalDateTime

data class NoteEntry (
    val id: ObjectId = ObjectId(),
    val title: String = "Title",
    val content: String = "Content",
    val date: LocalDateTime = LocalDateTime.now(),
    val isSelected: Boolean = false
)