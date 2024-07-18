package com.example.emergetestapplication.emerge.presentation.view.compose

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arun.emergetestapplication.R
import com.example.emergetestapplication.emerge.common.AppConstants
import com.example.emergetestapplication.emerge.data.model.firebase.FbCategoryModel
import com.example.emergetestapplication.emerge.data.model.firebase.FbMovieModel
import com.example.emergetestapplication.emerge.presentation.view.state.SearchUserScreenState
import com.example.emergetestapplication.ui.theme.EmergeTestApplicationTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchUsersScreen(
    searchUserScreenState: SearchUserScreenState,
    searchUserCategories: (String) -> Unit,
    clearUserSearch: () -> Unit,
) {
    var query by remember { mutableStateOf(TextFieldValue("")) }
    val coroutineScope = rememberCoroutineScope()
    var searchJob by remember { mutableStateOf<Job?>(null) }
    val context = LocalContext.current

    DisposableEffect(Unit) {
        onDispose {
            query = TextFieldValue("")
            clearUserSearch()
        }
    }

    Column(
        modifier =
            Modifier
                .background(color = colorResource(id = R.color.white))
                .fillMaxSize()
                .padding(horizontal = 16.dp),
    ) {
        SearchTextField(
            query = query,
            onQueryChange = {
                query = it
                searchJob?.cancel()
                searchJob =
                    coroutineScope.launch {
                        delay(500)
                        if (query.text.isNotEmpty()) {
                            searchUserCategories(query.text)
                        }
                    }
            },
            label = stringResource(id = R.string.hint_search_by_username),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            searchUserScreenState.isLoading -> {
                CircularProgressIndicator(
                    modifier =
                        Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(24.dp)
                            .align(Alignment.CenterHorizontally),
                    color = colorResource(id = R.color.teal_700),
                )
            }

            searchUserScreenState.errorMessage != null -> {
                Toast.makeText(context, AppConstants.TOAST_NO_RESULT_FOUND, Toast.LENGTH_SHORT).show()

                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                ) {
                    Text(
                        modifier =
                            Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 50.dp),
                        fontSize = 16.sp,
                        text = "Error: ${searchUserScreenState.errorMessage}",
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_generic_error),
                        contentDescription = "Generic Error",
                        modifier =
                            Modifier
                                .size(100.dp)
                                .padding(top = 30.dp),
                    )
                }
            }

            else -> {
                if (!searchUserScreenState.userCategories.isNullOrEmpty()) {
                    CategoryList(
                        categories = searchUserScreenState.userCategories,
                        onDeleteCategory = { /*Do nothing*/ },
                        isDeleteCategoryEnabled = false,
                        isShowModifyButton = false,
                        onModifyCategory = { _ -> },
                    )
                } else {
                    Column(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                    ) {
                        Text(
                            modifier =
                                Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(top = 50.dp),
                            fontSize = 16.sp,
                            text = stringResource(id = R.string.username_search_hint),
                        )

                        Image(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "Search Image",
                            modifier =
                                Modifier
                                    .size(100.dp)
                                    .padding(top = 30.dp),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SearchUsersScreenPreview() {
    EmergeTestApplicationTheme {
        SearchUsersScreen(
            searchUserCategories = {},
            clearUserSearch = {},
            searchUserScreenState =
                SearchUserScreenState(
                    isLoading = false,
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
        )
    }
}
