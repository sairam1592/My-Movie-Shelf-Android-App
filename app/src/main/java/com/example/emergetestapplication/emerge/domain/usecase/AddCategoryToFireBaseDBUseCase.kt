package com.example.emergetestapplication.emerge.domain.usecase

import com.example.emergetestapplication.emerge.data.model.firebase.FbCategoryModel
import com.example.emergetestapplication.emerge.data.repository.movies.MovieRepository

class AddCategoryToFireBaseDBUseCase(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke(
        username: String,
        categoryName: String,
        category: FbCategoryModel,
    ) {
        repository.addCategoryToFireBaseDB(username, categoryName, category)
    }
}
