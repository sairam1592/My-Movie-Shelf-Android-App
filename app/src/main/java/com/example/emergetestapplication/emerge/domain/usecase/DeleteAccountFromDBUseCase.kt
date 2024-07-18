package com.example.emergetestapplication.emerge.domain.usecase

import com.example.emergetestapplication.emerge.data.repository.authentication.AuthRepository
import javax.inject.Inject

class DeleteAccountFromDBUseCase
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) {
        suspend operator fun invoke(username: String): Result<Unit> = authRepository.deleteAccount(username)
    }
