package com.example.notevault.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.notevault.NotesApplication
import com.example.notevault.data.UserPreferencesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _isToken = MutableStateFlow(false)
    val isToken: StateFlow<Boolean> = _isToken.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferencesRepository.token.collect {
                if (it.isNotEmpty()) _isToken.value = true
            }
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as NotesApplication)
                SplashViewModel(userPreferencesRepository = application.userPreferencesRepository)
            }
        }
    }
}