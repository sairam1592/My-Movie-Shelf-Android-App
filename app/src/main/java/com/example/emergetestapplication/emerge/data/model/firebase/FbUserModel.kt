package com.example.emergetestapplication.emerge.data.model.firebase

data class FbUserModel(
    val categories: Map<String, FbCategoryModel> = emptyMap(),
)
