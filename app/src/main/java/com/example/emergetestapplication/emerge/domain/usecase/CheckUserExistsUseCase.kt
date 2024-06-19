package com.example.emergetestapplication.emerge.domain.usecase

import com.example.emergetestapplication.emerge.data.repository.authentication.AuthRepository
import javax.inject.Inject

class CheckUserExistsUseCase
    @Inject
    constructor(
        private val repository: AuthRepository,
    ) {
        suspend operator fun invoke(username: String): Boolean = repository.checkUserExists(username)
    }
