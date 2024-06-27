package com.example.emergetestapplication.emerge.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emergetestapplication.emerge.data.model.firebase.FbCategoryModel
import com.example.emergetestapplication.emerge.domain.usecase.AddCategoryToFireBaseDBUseCase
import com.example.emergetestapplication.emerge.domain.usecase.DeleteAccountFromFirebaseUseCase
import com.example.emergetestapplication.emerge.domain.usecase.DeleteCategoryUseCase
import com.example.emergetestapplication.emerge.domain.usecase.GetUserCategoriesUseCase
import com.example.emergetestapplication.emerge.domain.usecase.RemoveMoviesFromCategoryUseCase
import com.example.emergetestapplication.emerge.domain.usecase.SearchMoviesUseCase
import com.example.emergetestapplication.emerge.presentation.view.state.HomeScreenState
import com.example.emergetestapplication.emerge.presentation.view.state.MoviesState
import com.example.emergetestapplication.emerge.presentation.view.state.SearchUserScreenState
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
        private val deleteCategoryUseCase: DeleteCategoryUseCase,
        private val removeMoviesFromCategoryUseCase: RemoveMoviesFromCategoryUseCase,
        private val deleteAccountFromFirebaseUseCase: DeleteAccountFromFirebaseUseCase,
        private val savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val _moviesState = MutableStateFlow(MoviesState())
        val moviesState = _moviesState.asStateFlow()

        private val _homeScreenState = MutableStateFlow(HomeScreenState())
        val homeScreenState = _homeScreenState.asStateFlow()

        private val _searchUserScreenState = MutableStateFlow(SearchUserScreenState())
        val searchUserScreenState = _searchUserScreenState.asStateFlow()

        private val _addCategoryState = MutableStateFlow<Result<Unit>?>(null)
        val addCategoryState = _addCategoryState.asStateFlow()

        private val _deleteCategoryState = MutableStateFlow<Result<Unit>?>(null)
        val deleteCategoryState = _deleteCategoryState.asStateFlow()

        private val _modifyCategoryState = MutableStateFlow<Result<Unit>?>(null)
        val modifyCategoryState = _modifyCategoryState.asStateFlow()

        private val _deleteAccountState = MutableStateFlow<Result<Unit>?>(null)
        val deleteAccountState = _deleteAccountState.asStateFlow()

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

        fun searchCategoriesByUser(username: String) {
            _searchUserScreenState.value = SearchUserScreenState(isLoading = true)
            viewModelScope.launch {
                runCatching {
                    getUserCategoriesUseCase(username).collect { userCategories ->
                        _searchUserScreenState.value =
                            SearchUserScreenState(isLoading = false, userCategories = userCategories)
                    }
                }.onFailure { exception ->
                    _searchUserScreenState.value =
                        SearchUserScreenState(isLoading = false, errorMessage = exception.message)
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

        fun deleteCategory(
            username: String,
            categoryName: String,
        ) {
            viewModelScope.launch {
                runCatching {
                    deleteCategoryUseCase(username, categoryName)
                }.onSuccess {
                    _deleteCategoryState.value = Result.success(Unit)
                }.onFailure { exception ->
                    _deleteCategoryState.value = Result.failure(exception)
                }
            }
        }

        fun resetDeleteCategoryState() {
            _deleteCategoryState.value = null
        }

        fun removeMoviesFromCategory(
            username: String,
            categoryName: String,
            movieIds: List<Int>,
        ) {
            viewModelScope.launch {
                runCatching {
                    removeMoviesFromCategoryUseCase(username, categoryName, movieIds)
                }.onSuccess {
                    getUserCategories(username)
                    _modifyCategoryState.value = Result.success(Unit)
                }.onFailure { exception ->
                    _modifyCategoryState.value = Result.failure(exception)
                }
            }
        }

        fun resetModifyCategoryState() {
            _modifyCategoryState.value = null
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

        fun deleteAccount(username: String) {
            viewModelScope.launch {
                runCatching {
                    deleteAccountFromFirebaseUseCase(username)
                }.onSuccess {
                    _deleteAccountState.value = Result.success(Unit)
                }.onFailure { exception ->
                    _deleteAccountState.value = Result.failure(exception)
                }
            }
        }

        fun resetDeleteAccountState() {
            _deleteAccountState.value = null
    }

        fun setErrorMessageForSearchScreen(errorMessage: String) {
            _searchUserScreenState.value =
                SearchUserScreenState(isLoading = false, errorMessage = errorMessage)
        }

        fun clearMoviesSearch() {
            _moviesState.value = MoviesState()
        }

        fun clearUserSearch() {
            _searchUserScreenState.value = SearchUserScreenState()
        }
    }
