package com.example.emergetestapplication.emerge.presentation.view.compose

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arun.emergetestapplication.R
import com.example.emergetestapplication.emerge.common.AppConstants
import com.example.emergetestapplication.emerge.data.model.firebase.FbCategoryModel
import com.example.emergetestapplication.emerge.data.model.firebase.FbMovieModel
import com.example.emergetestapplication.emerge.presentation.view.state.AuthState
import com.example.emergetestapplication.emerge.presentation.view.state.HomeScreenState
import com.example.emergetestapplication.ui.theme.EmergeTestApplicationTheme

@Composable
fun HomeScreen(
    authState: AuthState,
    homeScreenState: HomeScreenState,
    onLogout: () -> Unit,
    onLogoutSuccess: () -> Unit,
    onCreateListClick: () -> Unit,
    onSearchUsersClick: () -> Unit,
    getUserCategories: () -> Unit,
    deleteCategory: (FbCategoryModel) -> Unit,
    deleteCategoryState: Result<Unit>?,
    resetDeleteCategoryState: () -> Unit,
    onModifyCategory: (FbCategoryModel) -> Unit,
    modifyCategoryState: Result<Unit>?,
    resetModifyCategoryState: () -> Unit,
    onAboutClick: () -> Unit,
) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    var showMenu by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        getUserCategories()
    }

    LaunchedEffect(deleteCategoryState) {
        deleteCategoryState?.let {
            if (it.isSuccess) {
                Toast.makeText(context, AppConstants.TOAST_CATEGORY_DELETED, Toast.LENGTH_SHORT).show()
                resetDeleteCategoryState()
                getUserCategories()
            } else if (it.isFailure) {
                Toast
                    .makeText(context, AppConstants.TOAST_CATEGORY_DELETE_FAILED, Toast.LENGTH_SHORT)
                    .show()
                resetDeleteCategoryState()
            }
        }
    }

    LaunchedEffect(modifyCategoryState) {
        modifyCategoryState?.let {
            if (it.isSuccess) {
                Toast
                    .makeText(context, AppConstants.TOAST_CATEGORY_MODIFIED, Toast.LENGTH_SHORT)
                    .show()
                resetModifyCategoryState()
            } else if (it.isFailure) {
                Toast
                    .makeText(
                        context,
                        AppConstants.TOAST_CATEGORY_MODIFY_FAILED,
                        Toast.LENGTH_SHORT,
                    ).show()
                resetModifyCategoryState()
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = colorResource(id = R.color.white),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        style = MaterialTheme.typography.h6,
                        text = "Welcome ${authState.user?.username?.trim()}",
                    )
                },
                actions = {
                    Row(modifier = Modifier.wrapContentSize()) {
                        TextButton(
                            modifier = Modifier.padding(end = 10.dp),
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(.2.dp, Color.White),
                            colors =
                                ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(id = R.color.teal_700),
                                    contentColor = colorResource(id = R.color.white),
                                ),
                            onClick = {
                                onLogout()
                                Toast
                                    .makeText(
                                        context,
                                        AppConstants.TOAST_LOGOUT_SUCCESS,
                                        Toast.LENGTH_SHORT,
                                    ).show()
                            },
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 4.dp),
                                text = stringResource(id = R.string.logout),
                            )
                        }

                        IconButton(onClick = { showMenu = !showMenu }) {
                            Icon(Icons.Filled.MoreVert, contentDescription = "More")
                        }

                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false },
                        ) {
                            DropdownMenuItem(onClick = {
                                onAboutClick()
                                showMenu = false
                            }) {
                                Text(text = "About App")
                            }
                        }
                    }
                },
                backgroundColor = colorResource(id = R.color.teal_700),
                contentColor = Color.White,
            )
        },
        content = { padding ->
            Box(
                modifier =
                    Modifier
                        .background(color = colorResource(id = R.color.white))
                        .padding(horizontal = 16.dp)
                        .fillMaxSize(),
            ) {
                if (!authState.isAuthenticated) {
                    onLogoutSuccess()
                }

                if (!homeScreenState.userCategories.isNullOrEmpty()) {
                    Column(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .align(Alignment.TopCenter),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        HomeButtonSection(
                            onCreateListClick = onCreateListClick,
                            onSearchUsersClick = onSearchUsersClick,
                        )

                        Text(
                            text = stringResource(id = R.string.your_favourite_lists_title),
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(top = 10.dp),
                        )

                        Text(
                            text = stringResource(id = R.string.list_subtitle),
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp),
                        )

                        CategoryList(
                            categories =
                                homeScreenState.userCategories,
                            onDeleteCategory = deleteCategory,
                            onModifyCategory = onModifyCategory,
                            isDeleteCategoryEnabled = true,
                            isShowModifyButton = true,
                        )
                    }
                } else {
                    Column(
                        modifier =
                            Modifier
                                .padding(16.dp)
                                .align(Alignment.TopCenter),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        HomeButtonSection(
                            onCreateListClick = onCreateListClick,
                            onSearchUsersClick = onSearchUsersClick,
                        )

                        Spacer(modifier = Modifier.height(150.dp))

                        Image(
                            painter = painterResource(id = R.drawable.create_list_empty_img),
                            contentDescription = "Create List Empty Image",
                            modifier =
                                Modifier
                                    .size(130.dp),
                        )

                        Text(
                            style = MaterialTheme.typography.h6,
                            modifier = Modifier.padding(top = 18.dp),
                            text = stringResource(id = R.string.start_creating_list),
                        )
                    }
                }
            }
        },
    )
}

@Composable
private fun HomeButtonSection(
    onCreateListClick: () -> Unit,
    onSearchUsersClick: () -> Unit,
) {
    Row(modifier = Modifier.padding(top = 16.dp)) {
        CTAButtonTealBorder(
            text = stringResource(id = R.string.btn_create_your_list),
            onClick = onCreateListClick,
            modifier = Modifier.wrapContentSize(),
        )
        Spacer(modifier = Modifier.width(16.dp))
        CTAButtonTealBorder(
            text = stringResource(id = R.string.btn_search_users),
            onClick = onSearchUsersClick,
            modifier = Modifier.wrapContentSize(),
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    EmergeTestApplicationTheme {
        HomeScreen(
            authState = AuthState(),
            homeScreenState =
                HomeScreenState(
                    userCategories =
                        listOf(
                            FbCategoryModel(
                                title = "Category 1",
                                emoji = "🎬",
                                movies =
                                    listOf(
                                        FbMovieModel(
                                            id = 1,
                                            title = "MoneyBall",
                                            overview = "Great movie about Baseball and Statistics.",
                                            posterPath = "/mCU60YrUli3VfPVPOMDg26BgdhR.jpg",
                                        ),
                                        FbMovieModel(
                                            id = 2,
                                            title = "The Dark Knight",
                                            overview = "A movie about Batman.",
                                            posterPath = "/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
                                        ),
                                    ),
                            ),
                            FbCategoryModel(
                                title = "Category 2",
                                emoji = "🎥",
                                movies =
                                    listOf(
                                        FbMovieModel(
                                            id = 1,
                                            title = "MoneyBall",
                                            overview = "Great movie about Baseball and Statistics.",
                                            posterPath = "/mCU60YrUli3VfPVPOMDg26BgdhR.jpg",
                                        ),
                                        FbMovieModel(
                                            id = 2,
                                            title = "The Dark Knight",
                                            overview = "A movie about Batman.",
                                            posterPath = "/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
                                        ),
                                    ),
                            ),
                        ),
                ),
            onLogout = {},
            onLogoutSuccess = {},
            onCreateListClick = {},
            onSearchUsersClick = {},
            getUserCategories = {},
            deleteCategory = {},
            deleteCategoryState = null,
            resetDeleteCategoryState = {},
            onModifyCategory = { _ -> },
            modifyCategoryState = null,
            resetModifyCategoryState = {},
            onAboutClick = {},
        )
    }
}

@Preview
@Composable
private fun HomeScreenEmptyPreview() {
    EmergeTestApplicationTheme {
        HomeScreen(
            authState = AuthState(),
            homeScreenState = HomeScreenState(),
            onLogout = {},
            onLogoutSuccess = {},
            onCreateListClick = {},
            onSearchUsersClick = {},
            getUserCategories = {},
            deleteCategory = {},
            deleteCategoryState = null,
            resetDeleteCategoryState = {},
            onModifyCategory = { _ -> },
            modifyCategoryState = null,
            resetModifyCategoryState = {},
            onAboutClick = {},
        )
    }
}
