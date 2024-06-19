package com.example.emergetestapplication.emerge.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emergetestapplication.emerge.data.model.firebase.FbCategoryModel
import com.example.emergetestapplication.emerge.domain.usecase.AddCategoryToFireBaseDBUseCase
import com.example.emergetestapplication.emerge.domain.usecase.GetUserCategoriesUseCase
import com.example.emergetestapplication.emerge.domain.usecase.SearchMoviesUseCase
import com.example.emergetestapplication.emerge.presentation.view.state.HomeScreenState
import com.example.emergetestapplication.emerge.presentation.view.state.MoviesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel
    @Inject
    constructor(
        private val searchMoviesUseCase: SearchMoviesUseCase,
        private val getUserCategoriesUseCase: GetUserCategoriesUseCase,
        private val addCategoryUseCase: AddCategoryToFireBaseDBUseCase,
        private val savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val _moviesState = MutableStateFlow(MoviesState())
        val moviesState = _moviesState.asStateFlow()

        private val _homeScreenState = MutableStateFlow(HomeScreenState())
        val homeScreenState = _homeScreenState.asStateFlow()

        private val _addCategoryState = MutableStateFlow<Result<Unit>?>(null)
        val addCategoryState = _addCategoryState.asStateFlow()

        companion object {
            private const val KEY_TITLE = "title"
            private const val KEY_EMOJI = "emoji"
        }

        val title: MutableStateFlow<String> = MutableStateFlow(savedStateHandle[KEY_TITLE] ?: "")
        val emoji: MutableStateFlow<String> = MutableStateFlow(savedStateHandle[KEY_EMOJI] ?: "")

        fun setTitle(value: String) {
            title.value = value
            savedStateHandle[KEY_TITLE] = value
        }

        fun setEmoji(value: String) {
            emoji.value = value
            savedStateHandle[KEY_EMOJI] = value
        }

        fun resetTitleAndEmoji() {
            title.value = ""
            emoji.value = ""
            savedStateHandle[KEY_TITLE] = ""
            savedStateHandle[KEY_EMOJI] = ""
        }

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
                    _addCategoryState.value = Result.success(Unit)
                    getUserCategories(username)
                }.onFailure { exception ->
                    _addCategoryState.value = Result.failure(exception)
                }
            }
        }

        fun resetAddCategoryState() {
            _addCategoryState.value = null
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
