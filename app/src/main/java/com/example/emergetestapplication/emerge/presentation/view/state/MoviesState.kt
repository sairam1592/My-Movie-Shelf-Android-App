package com.example.emergetestapplication.emerge.presentation.view.state

import com.example.emergetestapplication.emerge.data.model.movies.Movie

data class MoviesState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val errorMessage: String? = null,
)
