package com.example.emergetestapplication.emerge.domain.usecase

import com.example.emergetestapplication.emerge.data.repository.authentication.AuthRepository
import javax.inject.Inject

class SignUpUseCase
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) {
        suspend operator fun invoke(
            username: String,
            password: String,
        ): Result<Unit> = authRepository.signUp(username, password)
    }
