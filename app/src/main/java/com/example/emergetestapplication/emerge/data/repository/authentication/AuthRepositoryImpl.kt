package com.example.emergetestapplication.emerge.data.repository.authentication

import com.example.emergetestapplication.emerge.data.datasource.local.AuthLocalDataSource
import com.example.emergetestapplication.emerge.data.model.user.User
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val localDataSource: AuthLocalDataSource,
    ) : AuthRepository {
        override suspend fun signUp(
            username: String,
            password: String,
        ): Result<Unit> = localDataSource.signUp(username, password)

        override suspend fun checkUserExists(username: String): Boolean = localDataSource.checkUserExists(username)

        override suspend fun login(
            username: String,
            password: String,
        ): Result<User?> = localDataSource.login(username, password)

        override suspend fun logout(): Result<Unit> = localDataSource.logout()

        override suspend fun deleteAccount(username: String): Result<Unit> = localDataSource.deleteAccount(username)
    }
