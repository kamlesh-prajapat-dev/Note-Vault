package com.example.notevault.ui.screens.home.listhome

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
import com.example.notevault.data.model.note.ObjectId
import com.example.notevault.data.network.NoteApi
import com.example.notevault.data.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response

class NoteViewModel(
    userPreferencesRepository: UserPreferencesRepository,
    private val repository: NoteRepository = NoteRepository(NoteApi.create(userPreferencesRepository))
) : ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as NotesApplication)
                NoteViewModel(userPreferencesRepository = application.userPreferencesRepository)
            }
        }
    }

    private val _isLoading = MutableStateFlow(true)
    val isLoading : StateFlow<Boolean> = _isLoading.asStateFlow()
    private val _listOfNoteEntries = MutableStateFlow<List<NoteEntry>>(emptyList())
    val listOfNoteEntries: StateFlow<List<NoteEntry>> = _listOfNoteEntries.asStateFlow()

    private val _tokenTimeOut = MutableStateFlow(false)
    val tokenTimeOut: StateFlow<Boolean> = _tokenTimeOut.asStateFlow()

    // 1st Task - Load List of Note entries
    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val listOfNotes = getAllNotes()
                val body = listOfNotes.body()
                if (listOfNotes.isSuccessful && body != null) {
                    _listOfNoteEntries.update {oldList ->
                        val updatedList = body.map {
                            NoteEntry(id = it.id, title = it.title, content = it.content, date = it.date)
                        }
                        updatedList
                    }
                    _isLoading.value = false
                } else if(listOfNotes.isSuccessful && body == null) {
                    _isLoading.value = false
                } else if (listOfNotes.code() == 403) {
                    _tokenTimeOut.value = true
                    _isLoading.value = false
                }else {
                    _isLoading.value = false
                    val msg = ""
                }
            } catch (e: Exception) {
                val msg = e.message
            }
        }
    }

    private val _multipleSelectOption = MutableStateFlow(true)
    val multipleSelectOption: StateFlow<Boolean> = _multipleSelectOption.asStateFlow()

    private val _allSelected = MutableStateFlow(false)
    val allSelected: StateFlow<Boolean> = _allSelected.asStateFlow()


    fun toggleAllSelected(flag: Boolean) {
        _allSelected.value = flag
    }

    fun updateMultipleSelectOption(flag: Boolean) {
        _multipleSelectOption.value = flag
    }

    fun toggleSelection(noteId: ObjectId, isSelected: Boolean) {
        _listOfNoteEntries.value = _listOfNoteEntries.value.map {
            if (it.id == noteId) it.copy(
                isSelected = isSelected
            ) else it
        }
    }

    fun toggleAllList(flag: Boolean) {
        _listOfNoteEntries.value = listOfNoteEntries.value.map {
            it.copy(
                isSelected = flag
            )
        }
    }


    // Database Request Process
    suspend fun getAllNotes(): Response<List<NotesEntry>> {
        return repository.getNotes()
    }
}