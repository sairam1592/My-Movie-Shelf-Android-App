package com.example.emergetestapplication.emerge.data.model.firebase

data class FbCategoryModel(
    val title: String = "",
    val emoji: String = "",
    val movies: List<FbMovieModel> = emptyList()
)
