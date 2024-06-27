package com.example.emergetestapplication.emerge.data.repository.authentication

import com.example.emergetestapplication.emerge.data.model.user.User

interface AuthRepository {
    suspend fun signUp(
        username: String,
        password: String,
    ): Result<Unit>

    suspend fun checkUserExists(username: String): Boolean

    suspend fun login(
        username: String,
        password: String,
    ): Result<User?>

    suspend fun logout(): Result<Unit>

    suspend fun deleteAccount(username: String): Result<Unit>
}
