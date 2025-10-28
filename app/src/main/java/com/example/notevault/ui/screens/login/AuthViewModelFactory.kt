package com.example.notevault.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notevault.data.UserPreferencesRepository

class AuthViewModelFactory(private val userPrefs: UserPreferencesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(userPreferencesRepository = userPrefs) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
