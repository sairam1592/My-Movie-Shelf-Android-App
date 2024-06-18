package com.example.emergetestapplication.emerge.data.repository.movies

import com.example.emergetestapplication.emerge.data.model.firebase.FbCategoryModel
import com.example.emergetestapplication.emerge.data.model.firebase.FbUserModel
import com.example.emergetestapplication.emerge.data.model.movies.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<Result<MovieResponse>>

    fun searchMovies(query: String): Flow<Result<MovieResponse>>

    fun getUserCategories(username: String): Flow<FbUserModel?>

    suspend fun addCategoryToFireBaseDB(
        username: String,
        categoryName: String,
        category: FbCategoryModel,
    )
}
