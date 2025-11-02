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
import com.example.notevault.data.model.note.NotesEntry
import com.example.notevault.data.network.NoteApi
import com.example.notevault.data.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response
import java.time.LocalDateTime

class EachNoteViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val repository: NoteRepository = NoteRepository(NoteApi.create(userPreferencesRepository = userPreferencesRepository))
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as NotesApplication)
                EachNoteViewModel(userPreferencesRepository = application.userPreferencesRepository)
            }
        }
    }

    private val _isFocusedOnContent = MutableStateFlow(false)
    val isFocusedOnContent : StateFlow<Boolean> = _isFocusedOnContent.asStateFlow()

    fun onChangeIsFocusedOnContent (flag: Boolean) {
        _isFocusedOnContent.value = flag
    }

    private val _isFocusedOnTitle = MutableStateFlow(false)
    val isFocusedTitle: StateFlow<Boolean> = _isFocusedOnTitle.asStateFlow()

    fun onChangeIsFocusedOnTitle(flag: Boolean) {
        _isFocusedOnTitle.value = flag
    }

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _eachNote = MutableStateFlow(NoteEntry())
    val eachNote : StateFlow<NoteEntry> = _eachNote.asStateFlow()

    private val _dummyEntry = MutableStateFlow(NoteEntry())

    private val _noOfCharacters = MutableStateFlow(if (eachNote.value.content.isNotEmpty()) eachNote.value.content.length else 0)
    val noOfCharacters : StateFlow<Int> = _noOfCharacters.asStateFlow()

    fun updateNoOfCharacters(num: Int) {
        _noOfCharacters.value = num
    }

    // this function - get note entry from list of noteEntries when choose on note
    fun updateNote(noteEntry: NoteEntry) {
        _eachNote.update {
           noteEntry
        }

        _dummyEntry.update {
            noteEntry
        }

        _noOfCharacters.value = noteEntry.content.length
    }

    fun updateContent(newContent: String) {
        _eachNote.update {
            it.copy(
                content = newContent,
                date = LocalDateTime.now()
            )
        }
    }

    fun updateTitle(newTitle: String) {
        _eachNote.update {
            it.copy(
                title = newTitle,
                date = LocalDateTime.now()
            )
        }
    }

    // this function save noteEntry in DB
    fun updateNoteEntry() {
        // 1st Task
        _isLoading.value = true

        // 2nd Task
        if (_dummyEntry.value == _eachNote.value) {
            _isLoading.value = false
            return
        }

        // 3rd Task
        val updatedNoteEntry: NoteEntry = eachNote.value
        val newEntry = NotesEntry(id = updatedNoteEntry.id, title = updatedNoteEntry.title, content = updatedNoteEntry.content, date = LocalDateTime.now())

        // 4th Task
        viewModelScope.launch {
            try {
                val updatedEntry = updateEntry(newEntry)
                val body = updatedEntry.body()
                if (updatedEntry.isSuccessful && body != null) {
                    updateNote(NoteEntry(body.id, body.title, body.content, body.date))
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _isLoading.value = false
                val msg = e.message
            }
        }
    }

    // Database Task
    private suspend fun updateEntry(notesEntry: NotesEntry): Response<NotesEntry> {
        return repository.updateNote(notesEntry.id, notesEntry)
    }
}