package com.example.emergetestapplication.emerge.data.datasource.remote

import com.example.emergetestapplication.emerge.data.model.movies.MovieResponse
import com.example.emergetestapplication.emerge.data.network.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MoviesRemoteDataSourceImpl
    @Inject
    constructor(
        private val apiService: APIService,
    ) : MoviesRemoteDataSource {
        override fun getPopularMovies(): Flow<Result<MovieResponse>> =
            flow {
                emit(Result.success(apiService.getPopularMovies()))
            }.flowOn(Dispatchers.IO)
    }
