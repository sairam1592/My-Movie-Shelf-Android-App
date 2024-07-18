package com.example.emergetestapplication.emerge.presentation.view.state

import com.example.emergetestapplication.emerge.data.model.firebase.FbCategoryModel

data class SearchUserScreenState(
    val userCategories: List<FbCategoryModel>? = emptyList(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)