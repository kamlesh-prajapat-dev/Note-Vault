package com.example.notevault.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.notevault.NotesApplication
import com.example.notevault.data.UserPreferencesRepository
import com.example.notevault.data.model.user.LoginResponse
import com.example.notevault.data.model.user.User
import com.example.notevault.data.network.UserApi
import com.example.notevault.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewModel(
    private val repository: UserRepository = UserRepository(UserApi.retrofitService),
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isLogged = MutableStateFlow(false)
    val isLogged: StateFlow<Boolean> = _isLogged.asStateFlow()

    // Factory
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as NotesApplication)
                AuthViewModel(userPreferencesRepository = application.userPreferencesRepository)
            }
        }
    }

    // 1st Task - Take user input
    private val _request = MutableStateFlow(User())
    val request: StateFlow<User> = _request.asStateFlow()

    fun updateEmail(email: String) {
        _request.value = _request.value.copy(
            userName = email
        )
    }

    fun updatePassword(password: String) {
        _request.value = _request.value.copy(
            password = password
        )
    }

    // 2nd Task - Process Login Request...
    fun loginRequest()  {
        // 1st Task - Set Loading UiState
        _isLoading.value = true

        // 2nd Task - Validate User input
        _errorMsgForEmail.value = validateEmail(request.value.userName)

        _errorMsgForPassword.value = validatePassword(request.value.password)

        if (!(errorMsgForEmail.value.isEmpty() || errorMsgForPassword.value.isEmpty())) {
            _isLoading.value = false
            return
        }

        // 3rd Task - Database login process and return Login Response
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = loginUser(request.value)
                if (response.isSuccessful && response.body() != null) {
                    userPreferencesRepository.saveToken(response.body()?.token ?: "")
                } else {
                    _errorMsgForPassword.value = "Invalid credentials."
                }
            } catch (e: Exception) {
                _errorMsgForPassword.value = e.message.toString()
            } finally {
                // 4th Task - Return Login Screen with Success or Error UiState
                if (errorMsgForEmail.value.isEmpty() && errorMsgForPassword.value.isEmpty()) {
                    _isLogged.value = true
                    _isLoading.value = false
                } else {
                    _isLogged.value = false
                    _isLoading.value = false
                }
            }
        }
    }

    private val _errorMsgForEmail = MutableStateFlow("")
    val errorMsgForEmail : StateFlow<String> = _errorMsgForEmail.asStateFlow()

    private val _errorMsgForPassword = MutableStateFlow("")
    val errorMsgForPassword : StateFlow<String> = _errorMsgForPassword.asStateFlow()

    // Email Validation
    fun validateEmail(email: String): String {
        if (email.isEmpty()) return "Email must not be empty." else if (!email.matches(Regex("^([A-Za-z0-9._-]){2,}@[A-Za-z0-9.-]{2,}\\.[A-Za-z]{2,}$"))) return "Invalid email address"

        return ""
    }

    // Password Validation
    fun validatePassword(password: String) : String {
        if (password.isBlank()) return "Password must not be empty."

        return ""
    }

    // Database request process
    suspend fun loginUser(loginRequest: User) : Response<LoginResponse> {
        return repository.loginUser(loginRequest)
    }
}