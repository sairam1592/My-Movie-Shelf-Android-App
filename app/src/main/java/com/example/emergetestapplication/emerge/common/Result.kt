package com.example.emergetestapplication.emerge.common

sealed class Result<out T> {
    object Loading : Result<Nothing>()

    data class Success<out T>(
        val data: T,
    ) : Result<T>()

    data class Error(
        val exception: Throwable,
    ) : Result<Nothing>()
}
