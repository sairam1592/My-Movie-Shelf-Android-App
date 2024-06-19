package com.example.emergetestapplication.emerge.data.repository.movies

import com.example.emergetestapplication.emerge.data.model.firebase.FbCategoryModel
import com.example.emergetestapplication.emerge.data.model.movies.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun searchMovies(query: String): Flow<Result<MovieResponse>>

    fun getUserCategories(username: String): Flow<List<FbCategoryModel>?>

    suspend fun addCategoryToFireBaseDB(
        username: String,
        categoryName: String,
        category: FbCategoryModel,
    )
}
