package com.example.notevault.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notevault.data.model.user.User
import com.example.notevault.data.network.UserApi
import com.example.notevault.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignupViewModel(
    private val userRepository: UserRepository = UserRepository(UserApi.retrofitService)
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _user = MutableStateFlow(User())
    val user : StateFlow<User> = _user.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword : StateFlow<String> = _confirmPassword.asStateFlow()

    private val _isSignUp = MutableStateFlow(false)
    val isSignUp: StateFlow<Boolean> = _isSignUp.asStateFlow()

    fun updateEmail(email : String) {
        _user.update {
            it.copy(
                userName = email
            )
        }
    }

    fun updatePassword(password: String) {
        _user.update {
            it.copy(
                password = password
            )
        }
    }

    fun updateCP(cp: String) {
        _confirmPassword.update {
            cp
        }
    }

    private val _errorMsgForEmail = MutableStateFlow("")
    val errorMsgForEmail : StateFlow<String> = _errorMsgForEmail.asStateFlow()
    private val _errorMsgForPassword = MutableStateFlow("")
    val errorMsgForPassword: StateFlow<String> = _errorMsgForPassword.asStateFlow()

    private val _errorMsgForCP = MutableStateFlow("")
    val errorMsgForCP : StateFlow<String> = _errorMsgForCP.asStateFlow()

    fun validateEmail(email: String) : String {
        if (email.isEmpty()) return "Email must not be empty." else {
            if (!email.matches(Regex("^([A-Za-z0-9._-]){2,}@[A-Za-z0-9.-]{2,}\\.[A-Za-z]{2,}$"))) return "Invalid email address"
        }
        return ""
    }

    fun validatePassword(password: String) : String {
        if (password.isBlank()) return "Password must not be empty."  else {
            if (password.length < 8) return "Password must be at least 8 character"
            if (!password.any{ !it.isLetterOrDigit() }) return "Password must contain special characters"
        }
        return ""
    }

    fun validateCP(password: String, cp: String) : String {

        if (cp.isBlank() && cp.isBlank()) return "Confirm password must not be empty."

        if (cp.isBlank() && cp.isNotBlank()) return "Confirm password must not be empty."

        if (cp.isNotBlank() && password.isNotBlank()) if (cp != password) return "Passwords do not match."

        return ""
    }

    fun signUp() {
        // 1st Task - Set isLoading true
        _isLoading.value = true

        // 2nd Task - Validate user credentials
        _errorMsgForEmail.value = validateEmail(email =user.value.userName)

        _errorMsgForPassword.value = validatePassword(password = user.value.password)

        _errorMsgForCP.value = validateCP(cp = _confirmPassword.value, password = user.value.password)

        if (!(errorMsgForEmail.value.isEmpty() || errorMsgForPassword.value.isEmpty() || errorMsgForCP.value.isEmpty())) {
            _isLoading.value = false
            return
        }

        // 3rd Task - Database signup process and return response
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = databaseProcess(user.value)
                if (response.isSuccessful) {
                    _isSignUp.value = true
                    _isLoading.value = false
                } else {
                    _errorMsgForPassword.value = "Invalid credentials."
                    return@launch
                }
            } catch (e: Exception) {
                _errorMsgForPassword.value = e.message.toString()
            } finally {
                // 4th Task - Reset loading
                if (errorMsgForEmail.value.isEmpty() && errorMsgForPassword.value.isEmpty()) {
                    _isLoading.value = false
                } else {
                    _isLoading.value = false
                }
            }
        }
    }

    suspend fun databaseProcess(user: User) : retrofit2.Response<Unit> {
        return userRepository.signupUser(user)
    }
}