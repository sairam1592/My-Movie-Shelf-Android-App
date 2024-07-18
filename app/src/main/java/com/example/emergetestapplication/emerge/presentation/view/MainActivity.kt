package com.example.emergetestapplication.emerge.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.emergetestapplication.emerge.navigation.MoviesNavHost
import com.example.emergetestapplication.emerge.presentation.viewmodel.AuthViewModel
import com.example.emergetestapplication.emerge.presentation.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val moviesViewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            MoviesNavHost(
                navController = navController,
                authViewModel = authViewModel,
                moviesViewModel = moviesViewModel,
            )
        }
    }
}