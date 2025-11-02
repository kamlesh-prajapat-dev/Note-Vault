package com.example.notevault.data.repository

import com.example.notevault.data.model.note.NotesEntry
import com.example.notevault.data.network.NoteApiService

class NoteRepository(private val api: NoteApiService) {

    suspend fun getNotes()  = api.getNotes()

    suspend fun saveEntry(noteEntry: NotesEntry) = api.saveNote(noteEntry)
    suspend fun updateNote(id: String, noteEntry: NotesEntry) = api.updateNote(id = id, noteEntry = noteEntry)
}