package com.example.emergetestapplication.emerge.common

object AppConstants {
    // Error Strings
    const val ERROR_NO_ACCOUNT_FOUND = "No account found with these credentials"
    const val USER_SEARCH_WARNING = "Search for other users apart from your username"
    const val TOAST_LIST_SAVE_ERROR = "Failed to save the list, Try Again Later!"
    const val TOAST_CATEGORY_DELETE_FAILED = "Failed to delete the category, Try Again Later!"
    const val TOAST_NO_RESULT_FOUND = "No Result Found"
    const val ERROR_SIGNUP_FAILED = "Sign up failed"
    const val ACCOUNT_ALREADY_EXISTS = "Account already exists, please login."
    const val TOAST_CATEGORY_MODIFY_FAILED = "Failed to modify the category list, Try Again Later!"

    // Success Strings
    const val TOAST_LIST_SAVED = "List saved successfully!"
    const val TOAST_CATEGORY_DELETED = "Category Deleted Successfully!"
    const val TOAST_LOGOUT_SUCCESS = "Logout Successful"
    const val TOAST_LOGIN_SUCCESS = "Login Successful"
    const val TOAST_SIGNUP_SUCCESS = "Sign Up Successful"
    const val TOAST_CATEGORY_MODIFIED = "Category List Modified Successfully!"
    const val TOAST_ACCOUNT_DELETED = "Account deleted successfully!"

    // Firebase Strings
    const val FIREBASE_COLLECTION_NAME = "users"
    const val CATEGORY_TITLE = "title"
    const val CATEGORY_EMOJI = "emoji"
    const val CATEGORY_MOVIES = "movies"
    const val MOVIE_ID = "id"
    const val MOVIE_TITLE = "title"
    const val MOVIE_OVERVIEW = "overview"
    const val MOVIE_POSTER_PATH = "posterPath"

    // Database Strings
    const val DATABASE_NAME = "app_database"
    const val USERS_TABLE_NAME = "users"
    const val USERNAME = "username"
    const val PASSWORD = "password"

    // Network Strings
    const val BASE_URL = "https://api.themoviedb.org/3/"
}
