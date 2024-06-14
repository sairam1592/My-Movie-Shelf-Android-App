package com.example.emergetestapplication.emerge.presentation.view.compose

import com.example.emergetestapplication.emerge.data.model.User

data class AuthState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val errorMessage: String? = null,
) {
    val isAuthenticated: Boolean
        get() = user != null && errorMessage == null
}