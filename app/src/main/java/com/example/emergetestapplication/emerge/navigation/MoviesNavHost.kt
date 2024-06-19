package com.example.emergetestapplication.emerge.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.emergetestapplication.emerge.common.AppConstants
import com.example.emergetestapplication.emerge.data.model.firebase.FbCategoryModel
import com.example.emergetestapplication.emerge.data.model.movies.Movie
import com.example.emergetestapplication.emerge.domain.mapper.MovieMapper.toFbMovieModelModel
import com.example.emergetestapplication.emerge.presentation.view.compose.CreateListScreen
import com.example.emergetestapplication.emerge.presentation.view.compose.HomeScreen
import com.example.emergetestapplication.emerge.presentation.view.compose.LoginScreen
import com.example.emergetestapplication.emerge.presentation.view.compose.SearchMoviesScreen
import com.example.emergetestapplication.emerge.presentation.view.compose.SearchUsersScreen
import com.example.emergetestapplication.emerge.presentation.view.compose.SignUpScreen
import com.example.emergetestapplication.emerge.presentation.view.compose.StartUpScreen
import com.example.emergetestapplication.emerge.presentation.viewmodel.AuthViewModel
import com.example.emergetestapplication.emerge.presentation.viewmodel.MoviesViewModel

sealed class Screen(
    val route: String,
) {
    object Startup : Screen("startup")

    object Login : Screen("login")

    object Signup : Screen("signup")

    object Home : Screen("home")

    object CreateList : Screen("create_list")

    object SearchMovie : Screen("search_movie")

    object SearchUser : Screen("search_user")
}

@Composable
fun MoviesNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    moviesViewModel: MoviesViewModel,
) {
    val errorEventState by authViewModel.errorEvent.collectAsStateWithLifecycle()
    val authState by authViewModel.authState.collectAsStateWithLifecycle()
    val moviesState by moviesViewModel.moviesState.collectAsStateWithLifecycle()
    val homeScreenState by moviesViewModel.homeScreenState.collectAsStateWithLifecycle()
    val searchUserScreenState by moviesViewModel.searchUserScreenState.collectAsStateWithLifecycle()
    val addCategoryState by moviesViewModel.addCategoryState.collectAsStateWithLifecycle()
    val deleteCategoryState by moviesViewModel.deleteCategoryState.collectAsStateWithLifecycle()

    var selectedMovies by remember { mutableStateOf<List<Movie>>(emptyList()) }

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
                onSignUp = { username, password, onAccountExists, onSignUpSuccess ->
                    authViewModel.signUp(
                        username,
                        password,
                        onAccountExists,
                        onSignUpSuccess,
                    )
                },
                onSignUpSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                },
                onAccountExists = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Signup.route) { inclusive = true }
                    }
                },
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                authState = authState,
                errorEvent = errorEventState,
                onLogin = { username, password -> authViewModel.login(username, password) },
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                authState = authState,
                homeScreenState = homeScreenState,
                onLogout = { authViewModel.logout() },
                onLogoutSuccess = {
                    navController.navigate(Screen.Startup.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onCreateListClick = { navController.navigate(Screen.CreateList.route) },
                onSearchUsersClick = {
                    navController.navigate(Screen.SearchUser.route)
                },
                getUserCategories = {
                    authState.user?.let { it1 ->
                        moviesViewModel.getUserCategories(
                            it1.username,
                        )
                    }
                },
                deleteCategory = { category ->
                    moviesViewModel.deleteCategory(
                        authState.user?.username ?: "",
                        category.title,
                    )
                },
                deleteCategoryState = deleteCategoryState,
                resetDeleteCategoryState = { moviesViewModel.resetDeleteCategoryState() },
            )
        }

        composable(Screen.CreateList.route) {
            CreateListScreen(
                title = moviesViewModel.title.collectAsStateWithLifecycle().value,
                setTitle = { moviesViewModel.setTitle(it) },
                emoji = moviesViewModel.emoji.collectAsStateWithLifecycle().value,
                setEmoji = { moviesViewModel.setEmoji(it) },
                onAddMoviesClick = { navController.navigate(Screen.SearchMovie.route) },
                onSaveListClick = { title, emoji, movies ->
                    val fbMovies =
                        movies.map { movie ->
                            toFbMovieModelModel(movie)
                        }
                    val category =
                        FbCategoryModel(
                            emoji = emoji,
                            movies = fbMovies,
                        )
                    authState.user?.username?.let { it1 ->
                        moviesViewModel.addCategoryToFirebaseDB(
                            it1,
                            title,
                            category,
                        )
                    }
                },
                selectedMovies = selectedMovies,
                removeMovie = { movie -> selectedMovies = selectedMovies - movie },
                resetSelectedMovies = { selectedMovies = emptyList() },
                addCategoryState = addCategoryState,
                resetAddCategoryState = { moviesViewModel.resetAddCategoryState() },
                navController = navController,
            )
        }

        composable(Screen.SearchMovie.route) {
            SearchMoviesScreen(
                moviesState = moviesState,
                searchMovies = { query -> moviesViewModel.searchMovies(query) },
                onMovieSelected = { movie ->
                    selectedMovies = selectedMovies + movie
                    navController.popBackStack()
                },
                clearMoviesSearch = { moviesViewModel.clearMoviesSearch() },
            )
        }

        composable(Screen.SearchUser.route) {
            SearchUsersScreen(
                searchUserScreenState = searchUserScreenState,
                searchUserCategories = { query ->
                    if (query != authState.user?.username) {
                        moviesViewModel.searchCategoriesByUser(query)
                    } else {
                        moviesViewModel.setErrorMessageForSearchScreen(AppConstants.USER_SEARCH_WARNING)
                    }
                },
                clearUserSearch = { moviesViewModel.clearUserSearch() },
            )
        }
    }
}
