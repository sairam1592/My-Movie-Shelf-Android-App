package com.example.emergetestapplication.emerge.data.datasource.remote

import com.example.emergetestapplication.emerge.data.model.movies.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MoviesRemoteDataSource {
    fun getPopularMovies(): Flow<Result<MovieResponse>>

    fun searchMovies(query: String): Flow<Result<MovieResponse>>
}
