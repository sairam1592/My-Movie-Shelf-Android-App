package com.example.emergetestapplication.emerge.domain.usecase

import com.example.emergetestapplication.emerge.data.repository.movies.MovieRepository
import javax.inject.Inject

class DeleteAccountFromFirebaseUseCase
    @Inject
    constructor(
        private val movieRepository: MovieRepository,
    ) {
        suspend operator fun invoke(username: String): Result<Unit> = movieRepository.deleteAccountFromFirebase(username)
    }
