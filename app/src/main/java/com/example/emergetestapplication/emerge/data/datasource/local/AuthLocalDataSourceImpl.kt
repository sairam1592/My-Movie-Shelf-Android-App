package com.example.emergetestapplication.emerge.data.datasource.local

import com.example.emergetestapplication.emerge.data.helper.SharedPreferencesHelper
import com.example.emergetestapplication.emerge.data.model.User
import javax.inject.Inject

class AuthLocalDataSourceImpl
    @Inject
    constructor(
        private val sharedPreferencesHelper: SharedPreferencesHelper,
    ) : AuthLocalDataSource {
        override suspend fun signUp(
            username: String,
            password: String,
        ): Result<Unit> =
            runCatching {
                sharedPreferencesHelper.saveUser(username, password)
            }

        override suspend fun login(
            username: String,
            password: String,
        ): Result<User?> =
            runCatching {
                val user = sharedPreferencesHelper.getUser()
                if (user != null && user.username == username && user.password == password) {
                    user
                } else {
                    null
                }
            }

        override suspend fun logout(): Result<Unit> =
            runCatching {
                sharedPreferencesHelper.clearUser()
            }
    }
