package com.example.emergetestapplication.emerge.domain.usecase

import com.example.emergetestapplication.emerge.data.model.user.User
import com.example.emergetestapplication.emerge.data.repository.authentication.AuthRepository
import javax.inject.Inject

class LoginUseCase
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) {
        suspend operator fun invoke(
            username: String,
            password: String,
        ): Result<User?> = authRepository.login(username, password)
    }
