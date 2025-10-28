package com.example.notevault.ui.screens.home.listhome
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notevault.data.UserPreferencesRepository

class NoteViewModelFactory(private val userPrefs: UserPreferencesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(userPreferencesRepository = userPrefs) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
