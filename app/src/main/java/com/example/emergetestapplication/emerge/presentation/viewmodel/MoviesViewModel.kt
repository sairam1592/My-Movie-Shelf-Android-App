package com.example.emergetestapplication.emerge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emergetestapplication.emerge.domain.usecase.GetPopularMoviesUseCase
import com.example.emergetestapplication.emerge.presentation.view.state.MoviesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel
    @Inject
    constructor(
        private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    ) : ViewModel() {
        private val _moviesState = MutableStateFlow(MoviesState())
        val moviesState: StateFlow<MoviesState> = _moviesState

        fun getPopularMovies() {
            viewModelScope.launch {
                _moviesState.value = MoviesState(isLoading = true)
                runCatching {
                    getPopularMoviesUseCase().first()
                }.onSuccess { movieResponse ->
                    _moviesState.value =
                        MoviesState(
                            isLoading = false,
                            movies = movieResponse.getOrNull()?.results ?: emptyList(),
                        )
                }.onFailure { exception ->
                    _moviesState.value =
                        MoviesState(isLoading = false, errorMessage = exception.message)
                }
            }
        }
    }
