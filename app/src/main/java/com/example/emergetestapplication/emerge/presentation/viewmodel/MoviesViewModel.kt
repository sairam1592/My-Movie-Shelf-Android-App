package com.example.emergetestapplication.emerge.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emergetestapplication.emerge.data.model.firebase.FbCategoryModel
import com.example.emergetestapplication.emerge.domain.usecase.AddCategoryToFireBaseDBUseCase
import com.example.emergetestapplication.emerge.domain.usecase.GetPopularMoviesUseCase
import com.example.emergetestapplication.emerge.domain.usecase.GetUserCategoriesUseCase
import com.example.emergetestapplication.emerge.domain.usecase.SearchMoviesUseCase
import com.example.emergetestapplication.emerge.presentation.view.state.HomeScreenState
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
        private val searchMoviesUseCase: SearchMoviesUseCase,
        private val getUserCategoriesUseCase: GetUserCategoriesUseCase,
        private val addCategoryUseCase: AddCategoryToFireBaseDBUseCase,
    ) : ViewModel() {
        private val _moviesState = MutableStateFlow(MoviesState())
        val moviesState: StateFlow<MoviesState> = _moviesState

        private val _homeScreenState = MutableStateFlow(HomeScreenState())
        val homeScreenState: StateFlow<HomeScreenState> = _homeScreenState

        fun getUserCategories(username: String) {
            _homeScreenState.value = HomeScreenState(isLoading = true)
            viewModelScope.launch {
                runCatching {
                    getUserCategoriesUseCase(username).collect { userCategories ->
                        _homeScreenState.value = HomeScreenState(isLoading = false, userCategories = userCategories)
                    }
                }.onFailure { exception ->
                    _homeScreenState.value = HomeScreenState(isLoading = false, errorMessage = exception.message)
                }
            }
        }

        fun addCategoryToFirebaseDB(
            username: String,
            categoryName: String,
            category: FbCategoryModel,
        ) {
            viewModelScope.launch {
                runCatching {
                    addCategoryUseCase(username, categoryName, category)
                }.onSuccess {
                    getUserCategories(username)
                }.onFailure { exception ->
                //TODO Handle error
            }
        }
    }

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

        fun searchMovies(query: String) {
            viewModelScope.launch {
                _moviesState.value = MoviesState(isLoading = true)
                runCatching {
                    searchMoviesUseCase(query).first()
                }.onSuccess { searchResponse ->
                    _moviesState.value =
                        MoviesState(
                            isLoading = false,
                            movies = searchResponse.getOrNull()?.results ?: emptyList(),
                        )
                }.onFailure { exception ->
                    _moviesState.value =
                        MoviesState(isLoading = false, errorMessage = exception.message)
            }
        }
    }

    fun clearMoviesSearch() {
        _moviesState.value = MoviesState()
    }

    }
