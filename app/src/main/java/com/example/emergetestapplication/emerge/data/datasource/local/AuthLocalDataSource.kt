package com.example.emergetestapplication.emerge.data.datasource.local

import com.example.emergetestapplication.emerge.data.model.User

interface AuthLocalDataSource {
    suspend fun signUp(
        username: String,
        password: String,
    ): Result<Unit>

    suspend fun login(
        username: String,
        password: String,
    ): Result<User?>

    suspend fun logout(): Result<Unit>
}
