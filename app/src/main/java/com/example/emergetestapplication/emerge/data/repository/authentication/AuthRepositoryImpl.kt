package com.example.emergetestapplication.emerge.data.repository.authentication

import com.example.emergetestapplication.emerge.data.datasource.local.AuthLocalDataSource
import com.example.emergetestapplication.emerge.data.model.user.User
import com.example.emergetestapplication.emerge.data.repository.authentication.AuthRepository
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

        override suspend fun login(
            username: String,
            password: String,
        ): Result<User?> = localDataSource.login(username, password)

        override suspend fun logout(): Result<Unit> = localDataSource.logout()
    }