package com.example.emergetestapplication.emerge.presentation.view.state

import com.example.emergetestapplication.emerge.data.model.firebase.FbUserModel

data class HomeScreenState(
    val userCategories: FbUserModel? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)