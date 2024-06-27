package com.example.emergetestapplication.emerge.data.repository.movies

import com.example.emergetestapplication.emerge.data.datasource.remote.MoviesRemoteDataSource
import com.example.emergetestapplication.emerge.data.model.firebase.FbCategoryModel
import com.example.emergetestapplication.emerge.data.model.movies.MovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl
    @Inject
    constructor(
        private val moviesRemoteDataSource: MoviesRemoteDataSource,
    ) : MovieRepository {
        override fun searchMovies(query: String): Flow<Result<MovieResponse>> = moviesRemoteDataSource.searchMovies(query)

        override fun getUserCategories(username: String): Flow<List<FbCategoryModel>?> =
            flow {
                emit(moviesRemoteDataSource.getUserCategories(username))
            }

        override suspend fun addCategoryToFireBaseDB(
            username: String,
            categoryName: String,
            category: FbCategoryModel,
        ) {
            moviesRemoteDataSource.addCategoryToFirebaseDB(username, categoryName, category)
        }

        override suspend fun deleteCategory(
            username: String,
            categoryName: String,
        ) {
            moviesRemoteDataSource.deleteCategory(username, categoryName)
        }

        override suspend fun removeMoviesFromCategory(
            username: String,
            categoryName: String,
            movieIds: List<Int>,
        ) {
            moviesRemoteDataSource.removeMoviesFromCategory(username, categoryName, movieIds)
    }

        override suspend fun deleteAccountFromFirebase(username: String): Result<Unit> =
            moviesRemoteDataSource.deleteAccountFromFirebase(username)
    }
