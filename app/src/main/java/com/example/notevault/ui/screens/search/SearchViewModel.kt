package com.example.notevault.ui.screens.search

import androidx.lifecycle.ViewModel
import com.example.notevault.data.model.note.NoteEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SearchViewModel : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    private val _listOfNoteEntries = MutableStateFlow<List<NoteEntry>>(emptyList())
    private val _filteredNoteEntries = MutableStateFlow<List<NoteEntry>>(emptyList())
    val filteredNoteEntries: StateFlow<List<NoteEntry>> = _filteredNoteEntries.asStateFlow()

    fun getListOfNoteEntries(listOfNoteEntries: List<NoteEntry>) {
        _listOfNoteEntries.update {
            listOfNoteEntries
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        filterNotes()
    }

    private fun filterNotes() {
        val query = _searchQuery.value.lowercase()
        if (query.isBlank()) return

        val filterListOfNotes = _listOfNoteEntries.value.filter { noteEntry ->
            noteEntry.title.lowercase().contains(query)
            noteEntry.content.lowercase().contains(query)
        }

        _filteredNoteEntries.update {
            filterListOfNotes
        }
    }
}