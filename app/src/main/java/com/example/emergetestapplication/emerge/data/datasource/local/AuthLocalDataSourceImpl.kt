package com.example.emergetestapplication.emerge.data.datasource.local

import com.example.emergetestapplication.emerge.data.model.db.UserDao
import com.example.emergetestapplication.emerge.data.model.user.User
import com.example.emergetestapplication.emerge.domain.mapper.UserEntityToModelMapper.toEntity
import com.example.emergetestapplication.emerge.domain.mapper.UserEntityToModelMapper.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthLocalDataSourceImpl
    @Inject
    constructor(
        private val userDao: UserDao,
    ) : AuthLocalDataSource {
        override suspend fun signUp(
            username: String,
            password: String,
        ): Result<Unit> =
            withContext(
                Dispatchers.IO,
            ) {
                userDao.insertUser(User(username = username, password = password).toEntity())
                Result.success(Unit)
            }

        override suspend fun login(
            username: String,
            password: String,
        ): Result<User?> =
            withContext(Dispatchers.IO) {
                val userEntity = userDao.getUser(username, password)
                if (userEntity != null) {
                    Result.success(userEntity.toModel())
                } else {
                    Result.failure(Exception("No account found with these credentials"))
                }
            }

        override suspend fun logout(): Result<Unit> = Result.success(Unit)
    }
