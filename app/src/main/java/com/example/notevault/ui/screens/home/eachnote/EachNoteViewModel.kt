package com.example.notevault.ui.screens.home.eachnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.notevault.NotesApplication
import com.example.notevault.data.UserPreferencesRepository
import com.example.notevault.data.model.note.NoteEntry
import com.example.notevault.data.network.NoteApi
import com.example.notevault.data.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class EachNoteViewModel(
    userPreferencesRepository: UserPreferencesRepository,
    private val repository: NoteRepository = NoteRepository(NoteApi.create(userPreferencesRepository))
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as NotesApplication)
                EachNoteViewModel(userPreferencesRepository = application.userPreferencesRepository)
            }
        }
    }

    private val _eachNote = MutableStateFlow(NoteEntry())
    val eachNote : StateFlow<NoteEntry> = _eachNote.asStateFlow()

    private val _noOfCharacters = MutableStateFlow(if (eachNote.value.content.isNotEmpty()) eachNote.value.content.length else 0)
    val noOfCharacters : StateFlow<Int> = _noOfCharacters.asStateFlow()

    fun updateNoOfCharacters(num: Int) {
        _noOfCharacters.value = num
    }


    // this function - get note entry from list of noteEntries when choose on note
    fun updateNote(noteEntry: NoteEntry) {
        _eachNote.update {
            it.copy(
                id = noteEntry.id,
                title = noteEntry.title,
                content = noteEntry.content,
                date = noteEntry.date
            )
        }
    }

    fun updateContent(newContent: String) {
        _eachNote.update {
            it.copy(
                id = it.id,
                title = it.title,
                content = newContent,
                date = LocalDateTime.now()
            )
        }
    }

    fun updateTitle(newTitle: String) {
        _eachNote.update {
            it.copy(
                id = it.id,
                title = when(it.title) {
                    "Title" -> newTitle
                    else -> it.title + newTitle
                },
                content = it.content,
                date = LocalDateTime.now()
            )
        }
    }

    // this function save noteEntry in DB
    fun updateNoteEntry() {
        var updatedNoteEntry: NoteEntry = eachNote.value
        viewModelScope.launch {
            try {
                updatedNoteEntry = repository.updateNote(noteEntry = eachNote.value)
            } catch (e: Exception) {

            }
        }
        updateNote(updatedNoteEntry)
    }

}