package com.example.notevault.data.repository

import com.example.notevault.data.model.note.NoteEntry
import com.example.notevault.data.network.NoteApiService

class NoteRepository(private val api: NoteApiService) {

    suspend fun getNotes()  = api.getNotes()

    suspend fun updateNote(noteEntry: NoteEntry) = api.updateNote(noteEntry = noteEntry)
}