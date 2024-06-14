package com.example.emergetestapplication.emerge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emergetestapplication.emerge.data.model.User
import com.example.emergetestapplication.emerge.domain.usecase.LoginUseCase
import com.example.emergetestapplication.emerge.domain.usecase.LogoutUseCase
import com.example.emergetestapplication.emerge.domain.usecase.SignUpUseCase
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
        private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
        val authState: StateFlow<AuthState> = _authState.asStateFlow()

        fun signUp(
            username: String,
            password: String,
        ) {
            viewModelScope.launch {
                _authState.value = AuthState.Loading
                runCatching {
                    signUpUseCase(username, password)
                }.onSuccess {
                    _authState.value = AuthState.Authenticated(null)
                }.onFailure { exception ->
                    _authState.value = AuthState.Error(exception.message ?: "Sign up failed")
                }
            }
        }

        fun login(
            username: String,
            password: String,
        ) {
            viewModelScope.launch {
                _authState.value = AuthState.Loading
                runCatching {
                    loginUseCase(username, password)
                }.onSuccess { result ->
                    _authState.value = AuthState.Authenticated(result.getOrNull())
                }.onFailure { exception ->
                    _authState.value = AuthState.Error(exception.message ?: "Login failed")
                }
            }
        }

        fun logout() {
            viewModelScope.launch {
                runCatching {
                    logoutUseCase()
                }.onSuccess {
                    _authState.value = AuthState.Unauthenticated
                }.onFailure { exception ->
                    _authState.value = AuthState.Error(exception.message ?: "Logout failed")
                }
            }
        }
    }

sealed class AuthState {
    object Idle : AuthState()

    object Loading : AuthState()

    data class Authenticated(
        val user: User?,
    ) : AuthState()

    object Unauthenticated : AuthState()

    data class Error(
        val message: String,
    ) : AuthState()
}
