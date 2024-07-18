package com.example.emergetestapplication.emerge.domain.usecase

import com.example.emergetestapplication.emerge.data.repository.movies.MovieRepository

class DeleteCategoryUseCase(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke(
        username: String,
        categoryName: String,
    ) {
        repository.deleteCategory(username, categoryName)
    }
}
