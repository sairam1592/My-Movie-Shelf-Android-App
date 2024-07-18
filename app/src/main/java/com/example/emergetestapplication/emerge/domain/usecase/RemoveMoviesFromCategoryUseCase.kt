package com.example.emergetestapplication.emerge.domain.usecase

import com.example.emergetestapplication.emerge.data.repository.movies.MovieRepository
import javax.inject.Inject

class RemoveMoviesFromCategoryUseCase
    @Inject
    constructor(
        private val repository: MovieRepository,
    ) {
        suspend operator fun invoke(
            username: String,
            categoryName: String,
            movieIds: List<Int>,
        ) = repository.removeMoviesFromCategory(username, categoryName, movieIds)
    }
