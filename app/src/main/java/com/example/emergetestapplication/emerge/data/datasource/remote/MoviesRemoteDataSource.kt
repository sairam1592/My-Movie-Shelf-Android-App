package com.example.emergetestapplication.emerge.data.datasource.remote

import com.example.emergetestapplication.emerge.data.model.firebase.FbCategoryModel
import com.example.emergetestapplication.emerge.data.model.movies.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MoviesRemoteDataSource {
    fun searchMovies(query: String): Flow<Result<MovieResponse>>

    suspend fun getUserCategories(username: String): List<FbCategoryModel>?

    suspend fun addCategoryToFirebaseDB(
        username: String,
        categoryName: String,
        category: FbCategoryModel,
    )

    suspend fun deleteCategory(
        username: String,
        categoryName: String,
    )

    suspend fun removeMoviesFromCategory(
        username: String,
        categoryName: String,
        movieIds: List<Int>,
    )

    suspend fun deleteAccountFromFirebase(username: String): Result<Unit>
}
