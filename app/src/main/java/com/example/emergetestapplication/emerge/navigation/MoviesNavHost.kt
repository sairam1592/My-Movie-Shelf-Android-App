package com.example.emergetestapplication.emerge.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.emergetestapplication.emerge.common.AppConstants
import com.example.emergetestapplication.emerge.data.model.firebase.FbCategoryModel
import com.example.emergetestapplication.emerge.data.model.movies.Movie
import com.example.emergetestapplication.emerge.domain.mapper.MovieMappers.toFbMovieModel
import com.example.emergetestapplication.emerge.domain.mapper.MovieMappers.toMovie
import com.example.emergetestapplication.emerge.presentation.view.compose.AboutScreen
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

    object CreateList : Screen("create_list") {
        fun createRoute(
            title: String,
            emoji: String,
        ): String = "create_list?title=$title&emoji=$emoji"
    }

    object SearchMovie : Screen("search_movie")

    object SearchUser : Screen("search_user")

    object About : Screen("about")
}

@Composable
fun MoviesNavHost(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    moviesViewModel: MoviesViewModel,
) {
    val context = LocalContext.current
    var selectedMovies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    val deleteAccountState by moviesViewModel.deleteAccountState.collectAsStateWithLifecycle()

    LaunchedEffect(deleteAccountState) {
        deleteAccountState?.let {
            if (it.isSuccess) {
                Toast
                    .makeText(context, AppConstants.TOAST_ACCOUNT_DELETED, Toast.LENGTH_SHORT)
                    .show()
                authViewModel.logout()
                navController.navigate(Screen.Startup.route) {
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
                moviesViewModel.resetDeleteAccountState()
            } else if (it.isFailure) {
                Toast
                    .makeText(
                        context,
                        "Failed to delete account: ${it.exceptionOrNull()?.message}",
                        Toast.LENGTH_SHORT,
                    ).show()
                moviesViewModel.resetDeleteAccountState()
            }
        }
    }

    NavHost(navController = navController, startDestination = Screen.Startup.route) {
        composable(Screen.About.route) {
            AboutScreen(
                navController = navController,
                onDeleteAccount = {
                    authViewModel.authState.value.user?.let { user ->
                        authViewModel.deleteAccount(user.username)
                        moviesViewModel.deleteAccount(user.username)
                    }
                    navController.navigate(Screen.Startup.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
            )
        }

        composable(Screen.Startup.route) {
            StartUpScreen(
                onNavigateToLogin = { navController.navigate(Screen.Login.route) },
                onNavigateToSignUp = { navController.navigate(Screen.Signup.route) },
            )
        }

        composable(Screen.Signup.route) {
            val authState by authViewModel.authState.collectAsStateWithLifecycle()

            SignUpScreen(
                authState = authState,
                onSignUp = { username, password, onAccountExists, onSignUpSuccess ->
                    authViewModel.signUp(username, password, onAccountExists, onSignUpSuccess)
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
            val authState by authViewModel.authState.collectAsStateWithLifecycle()
            val errorEventState by authViewModel.errorEvent.collectAsStateWithLifecycle()

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
            val authState by authViewModel.authState.collectAsStateWithLifecycle()
            val homeScreenState by moviesViewModel.homeScreenState.collectAsStateWithLifecycle()
            val deleteCategoryState by moviesViewModel.deleteCategoryState.collectAsStateWithLifecycle()
            val modifyCategoryState by moviesViewModel.modifyCategoryState.collectAsStateWithLifecycle()

            HomeScreen(
                authState = authState,
                homeScreenState = homeScreenState,
                onLogout = { authViewModel.logout() },
                onLogoutSuccess = {
                    navController.navigate(Screen.Startup.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onCreateListClick = {
                    navController.navigate(
                        Screen.CreateList.createRoute(
                            "",
                            "",
                        ),
                    )
                },
                onSearchUsersClick = { navController.navigate(Screen.SearchUser.route) },
                getUserCategories = {
                    authState.user?.let { moviesViewModel.getUserCategories(it.username) }
                },
                deleteCategory = { category ->
                    moviesViewModel.deleteCategory(authState.user?.username ?: "", category.title)
                },
                deleteCategoryState = deleteCategoryState,
                resetDeleteCategoryState = { moviesViewModel.resetDeleteCategoryState() },
                onModifyCategory = { category ->
                    selectedMovies = category.movies.map { it.toMovie() }
                    navController.navigate(
                        Screen.CreateList.createRoute(
                            category.title,
                            category.emoji,
                        ),
                    )
                },
                modifyCategoryState = modifyCategoryState,
                resetModifyCategoryState = { moviesViewModel.resetModifyCategoryState() },
                onAboutClick = { navController.navigate(Screen.About.route) },
            )
        }

        composable(
            route = "${Screen.CreateList.route}?title={title}&emoji={emoji}",
            arguments =
                listOf(
                    navArgument("title") { type = NavType.StringType },
                    navArgument("emoji") { type = NavType.StringType },
                ),
        ) { backStackEntry ->

            val title = backStackEntry.arguments?.getString("title") ?: ""
            val emoji = backStackEntry.arguments?.getString("emoji") ?: ""

            val authState by authViewModel.authState.collectAsStateWithLifecycle()
            val addCategoryState by moviesViewModel.addCategoryState.collectAsStateWithLifecycle()

            CreateListScreen(
                isModify = title.isNotEmpty(),
                title = title.ifEmpty { moviesViewModel.title.collectAsStateWithLifecycle().value },
                setTitle = { moviesViewModel.setTitle(it) },
                emoji = emoji.ifEmpty { moviesViewModel.emoji.collectAsStateWithLifecycle().value },
                setEmoji = { moviesViewModel.setEmoji(it) },
                onAddMoviesClick = { navController.navigate(Screen.SearchMovie.route) },
                onSaveListClick = { title, emoji, movies ->
                    val fbMovies = movies.map { toFbMovieModel(it) }
                    val category = FbCategoryModel(emoji = emoji, movies = fbMovies)
                    authState.user?.username?.let {
                        moviesViewModel.addCategoryToFirebaseDB(
                            it,
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
            val moviesState by moviesViewModel.moviesState.collectAsStateWithLifecycle()

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
            val authState by authViewModel.authState.collectAsStateWithLifecycle()
            val searchUserScreenState by moviesViewModel.searchUserScreenState.collectAsStateWithLifecycle()

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
