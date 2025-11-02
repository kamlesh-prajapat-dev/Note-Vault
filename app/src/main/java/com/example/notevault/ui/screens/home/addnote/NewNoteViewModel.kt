package com.example.notevault.ui.screens.home.addnote

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response

class NewNoteViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val noteRepository: NoteRepository = NoteRepository(NoteApi.create(userPreferencesRepository = userPreferencesRepository))
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading : StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _noOfCharacters = MutableStateFlow(0)
    val noOfCharacters: StateFlow<Int> = _noOfCharacters.asStateFlow()

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

    fun updateNoOfCharacters(num: Int) {
        _noOfCharacters.value = num
    }

    fun reset() {
        _noOfCharacters.value = 0
        _noteEntry.update {
            NotesEntry()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as NotesApplication)
                NewNoteViewModel(userPreferencesRepository = application.userPreferencesRepository)
            }
        }
    }

    private val _noteEntry = MutableStateFlow(NotesEntry())
    val noteEntry: StateFlow<NotesEntry> = _noteEntry.asStateFlow()

    fun onChangeTitle(title: String) {
        _noteEntry.update {
            it.copy(
                title = title
            )
        }
    }

    fun onChangeContent(content: String) {
        _noteEntry.update {
            it.copy(
                content = content
            )
        }
    }

    fun createEntry() {
        // 1st Task
        _isLoading.value = true

        // 2nd Task
        if (_noteEntry.value.title.isNotBlank() && _noteEntry.value.content.isNotBlank()) {
            viewModelScope.launch(Dispatchers.IO) {
                val createdEntry = saveEntry()

                if (createdEntry.isSuccessful && createdEntry.body() != null) {
                    _isLoading.value = false
                } else {
                    _isLoading.value = false
                }
            }
        } else {
            _isLoading.value = false
        }
    }

    private suspend fun saveEntry(): Response<NotesEntry> {
        return noteRepository.saveEntry(_noteEntry.value)
    }
}