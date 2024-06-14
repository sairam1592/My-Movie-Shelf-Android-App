package com.example.emergetestapplication.emerge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.emergetestapplication.emerge.presentation.view.compose.LoginScreen
import com.example.emergetestapplication.emerge.presentation.view.compose.SignUpScreen
import com.example.emergetestapplication.emerge.presentation.view.compose.StartUpScreen
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
            StartUpScreen(
                onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                onNavigateToSignUp = { navController.navigate(Screen.Signup.route) },
            )
        }

        composable(Screen.Signup.route) {
            SignUpScreen(
                authState = authState,
                onSignUp = { username, password -> authViewModel.signUp(username, password) },
                onSignUpSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                },
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                authState = authState,
                onLogin = { username, password -> authViewModel.login(username, password) },
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
            )
        }
    }
}
