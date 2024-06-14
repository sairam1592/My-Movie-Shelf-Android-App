package com.example.emergetestapplication.emerge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.emergetestapplication.emerge.presentation.view.compose.StartupScreen
import com.example.emergetestapplication.emerge.presentation.viewmodel.AuthViewModel

sealed class Screen(
    val route: String,
) {
    object Startup : Screen("startup")

    object Login : Screen("login")

    object Signup : Screen("signup")

    object Home : Screen("home")
}

@Composable
fun AuthNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel,
) {
    val authState by authViewModel.authState.collectAsState()

    NavHost(navController = navController, startDestination = Screen.Startup.route) {
        composable(Screen.Startup.route) {
            StartupScreen(
                onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                onNavigateToSignUp = { navController.navigate(Screen.Signup.route) },
            )
        }
    }
}
