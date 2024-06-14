package com.example.emergetestapplication.emerge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emergetestapplication.emerge.data.model.User
import com.example.emergetestapplication.emerge.domain.usecase.LoginUseCase
import com.example.emergetestapplication.emerge.domain.usecase.LogoutUseCase
import com.example.emergetestapplication.emerge.domain.usecase.SignUpUseCase
import com.example.emergetestapplication.emerge.presentation.view.compose.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
    @Inject
    constructor(
        private val signUpUseCase: SignUpUseCase,
        private val loginUseCase: LoginUseCase,
        private val logoutUseCase: LogoutUseCase,
    ) : ViewModel() {
        private val _authState = MutableStateFlow(AuthState())
        val authState: StateFlow<AuthState> = _authState.asStateFlow()

        fun signUp(
            username: String,
            password: String,
        ) {
            viewModelScope.launch {
                _authState.value = _authState.value.copy(isLoading = true)
                runCatching {
                    signUpUseCase(username, password)
                }.onSuccess {
                    _authState.value =
                        _authState.value.copy(isLoading = false, user = User(username, password))
                }.onFailure { exception ->
                    _authState.value =
                        _authState.value.copy(
                            isLoading = false,
                            errorMessage = exception.message ?: "Sign up failed",
                        )
                }
            }
        }

        fun login(
            username: String,
            password: String,
        ) {
            viewModelScope.launch {
                _authState.value = _authState.value.copy(isLoading = true)
                runCatching {
                    loginUseCase(username, password)
                }.onSuccess { result ->
                    _authState.value =
                        _authState.value.copy(isLoading = false, user = result.getOrNull())
                }.onFailure { exception ->
                    _authState.value =
                        _authState.value.copy(
                            isLoading = false,
                            errorMessage = exception.message ?: "Login failed",
                        )
                }
            }
        }

        fun logout() {
            viewModelScope.launch {
                _authState.value = _authState.value.copy(isLoading = true)
                runCatching {
                    logoutUseCase()
                }.onSuccess {
                    _authState.value = _authState.value.copy(isLoading = false, user = null)
            }.onFailure { exception ->
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    errorMessage = exception.message ?: "Logout failed"
                )
            }
        }
    }
}
