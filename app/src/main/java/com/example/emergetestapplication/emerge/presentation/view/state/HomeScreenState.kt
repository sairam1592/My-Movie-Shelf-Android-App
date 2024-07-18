package com.example.emergetestapplication.emerge.presentation.view.state

import com.example.emergetestapplication.emerge.data.model.firebase.FbCategoryModel

data class HomeScreenState(
    val userCategories: List<FbCategoryModel>? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)
