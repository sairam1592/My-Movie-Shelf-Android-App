package com.example.emergetestapplication.emerge.data.model.movies

data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int,
)
