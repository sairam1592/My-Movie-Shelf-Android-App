package com.example.emergetestapplication.emerge.domain.usecase

import com.example.emergetestapplication.emerge.data.model.firebase.FbCategoryModel
import com.example.emergetestapplication.emerge.data.repository.movies.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetUserCategoriesUseCase(
    private val repository: MovieRepository,
) {
    operator fun invoke(username: String): Flow< List<FbCategoryModel>?> = repository.getUserCategories(username)
}
