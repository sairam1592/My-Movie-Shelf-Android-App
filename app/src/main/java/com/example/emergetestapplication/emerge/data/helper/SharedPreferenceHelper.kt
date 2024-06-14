package com.example.emergetestapplication.emerge.data.helper

import android.content.Context
import com.example.emergetestapplication.emerge.data.model.User
import javax.inject.Inject

class SharedPreferencesHelper
    @Inject
    constructor(
        context: Context,
    ) {
        private val sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

        fun saveUser(
            username: String,
            password: String,
        ) {
            sharedPreferences
                .edit()
                .putString("username", username)
                .putString("password", password)
                .apply()
        }

        fun getUser(): User? {
            val username = sharedPreferences.getString("username", null)
            val password = sharedPreferences.getString("password", null)
            return if (username != null && password != null) {
                User(username = username, password = password)
            } else {
                null
            }
        }

        fun clearUser() {
            sharedPreferences.edit().clear().apply()
        }
    }
