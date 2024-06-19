package com.example.emergetestapplication.emerge.presentation.view.state

import com.example.emergetestapplication.emerge.data.model.user.User

data class AuthState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val errorMessage: String? = null,
    val isAccountExists: Boolean = false,
) {
    val isAuthenticated: Boolean
        get() = user != null && errorMessage == null
}
