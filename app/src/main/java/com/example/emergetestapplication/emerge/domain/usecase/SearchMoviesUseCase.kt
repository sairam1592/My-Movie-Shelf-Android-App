package com.example.emergetestapplication.emerge.domain.usecase

import com.example.emergetestapplication.emerge.data.model.movies.MovieResponse
import com.example.emergetestapplication.emerge.data.repository.movies.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMoviesUseCase
    @Inject
    constructor(
        private val movieRepository: MovieRepository,
    ) {
        operator fun invoke(query: String): Flow<Result<MovieResponse>> = movieRepository.searchMovies(query)
    }
