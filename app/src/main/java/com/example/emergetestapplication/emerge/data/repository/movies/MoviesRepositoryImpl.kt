package com.example.emergetestapplication.emerge.data.repository.movies

import com.example.emergetestapplication.emerge.data.datasource.remote.MoviesRemoteDataSource
import com.example.emergetestapplication.emerge.data.model.movies.MovieResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl
    @Inject
    constructor(
        private val moviesRemoteDataSource: MoviesRemoteDataSource,
    ) : MovieRepository {
        override fun getPopularMovies(): Flow<Result<MovieResponse>> = moviesRemoteDataSource.getPopularMovies()

        override fun searchMovies(query: String): Flow<Result<MovieResponse>> = moviesRemoteDataSource.searchMovies(query)
    }
