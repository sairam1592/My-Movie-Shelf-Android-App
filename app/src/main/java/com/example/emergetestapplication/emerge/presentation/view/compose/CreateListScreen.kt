package com.example.emergetestapplication.emerge.presentation.view.compose

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arun.emergetestapplication.R
import com.example.emergetestapplication.emerge.common.AppConstants
import com.example.emergetestapplication.emerge.data.model.movies.Movie
import com.example.emergetestapplication.emerge.navigation.Screen
import com.example.emergetestapplication.ui.theme.EmergeTestApplicationTheme

@Composable
fun CreateListScreen(
    isModify: Boolean,
    title: String,
    setTitle: (String) -> Unit,
    emoji: String,
    setEmoji: (String) -> Unit,
    onAddMoviesClick: () -> Unit,
    onSaveListClick: (String, String, List<Movie>) -> Unit,
    resetSelectedMovies: () -> Unit,
    selectedMovies: List<Movie> = emptyList(),
    removeMovie: (Movie) -> Unit,
    addCategoryState: Result<Unit>?,
    resetAddCategoryState: () -> Unit,
    navController: NavHostController,
) {
    val context = LocalContext.current
    var isListCreated by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }

    Column(
        modifier =
            Modifier
                .background(color = colorResource(id = R.color.white))
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            text = stringResource(id = if (isModify) R.string.modify_your_list else R.string.create_a_list),
            style = MaterialTheme.typography.h6,
            fontSize = 24.sp,
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (!isListCreated && selectedMovies.isEmpty()) {
            OutlinedTextField(
                value = title,
                onValueChange = { setTitle(it) },
                label = { Text(stringResource(id = R.string.hint_list_title)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors =
                    TextFieldDefaults.outlinedTextFieldColors(
                        textColor = colorResource(id = R.color.black),
                        backgroundColor = colorResource(id = R.color.white),
                        focusedBorderColor = colorResource(id = R.color.teal_700),
                        unfocusedBorderColor = colorResource(id = R.color.teal_700),
                        cursorColor = colorResource(id = R.color.teal_700),
                        focusedLabelColor = colorResource(id = R.color.teal_700),
                    ),
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = emoji,
                onValueChange = { setEmoji(it) },
                label = { Text(stringResource(id = R.string.hint_list_emoji)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors =
                    TextFieldDefaults.outlinedTextFieldColors(
                        textColor = colorResource(id = R.color.black),
                        backgroundColor = colorResource(id = R.color.white),
                        focusedBorderColor = colorResource(id = R.color.teal_700),
                        unfocusedBorderColor = colorResource(id = R.color.teal_700),
                        cursorColor = colorResource(id = R.color.teal_700),
                        focusedLabelColor = colorResource(id = R.color.teal_700),
                    ),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { isListCreated = true },
                shape = RoundedCornerShape(16.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.teal_700),
                        contentColor = Color.White,
                    ),
                modifier = Modifier.fillMaxWidth(),
                enabled = emoji.isNotEmpty() && title.isNotEmpty(),
            ) {
                Text(text = stringResource(id = if (isModify) R.string.modify else R.string.create_list))
            }
        } else {
            MyNewListItem(
                isModify = isModify,
                emoji = emoji,
                title = title,
                onAddMoviesClick = onAddMoviesClick,
                onSaveListClick = {
                    onSaveListClick(title, emoji, selectedMovies)
                },
                selectedMovies = selectedMovies,
                removeMovie = removeMovie,
            )
        }
    }

    LaunchedEffect(addCategoryState) {
        addCategoryState?.let {
            if (it.isSuccess) {
                Toast.makeText(context, AppConstants.TOAST_LIST_SAVED, Toast.LENGTH_SHORT).show()
                navController.popBackStack(Screen.Home.route, false)
                setTitle("")
                setEmoji("")
                resetSelectedMovies()
                resetAddCategoryState()
            } else if (it.isFailure) {
                resetAddCategoryState()
                Toast.makeText(context, AppConstants.TOAST_LIST_SAVE_ERROR, Toast.LENGTH_SHORT).show()
            }
        }
    }

    // On Back press, dont hold onto any state values, clear everything
    BackHandler {
        if (isModify) {
            showConfirmDialog = true
        } else {
            navController.popBackStack()
            setTitle("")
            setEmoji("")
            resetSelectedMovies()
        }
    }

    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text(stringResource(id = R.string.unsaved_changes_title)) },
            text = { Text(stringResource(id = R.string.unsaved_changes_message)) },
            confirmButton = {
                TextButton(
                    colors =
                        ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.teal_700),
                            contentColor = Color.White,
                        ),
                    onClick = {
                        showConfirmDialog = false
                        navController.popBackStack()
                        setTitle("")
                        setEmoji("")
                        resetSelectedMovies()
                    },
                ) {
                    Text(stringResource(id = R.string.go_back))
                }
            },
            dismissButton = {
                TextButton(
                    colors =
                        ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.teal_700),
                            contentColor = Color.White,
                        ),
                    onClick = { showConfirmDialog = false },
                ) {
                    Text(stringResource(id = R.string.stay))
                }
            },
        )
    }
}

@Preview
@Composable
private fun CreateListScreenPreview() {
    EmergeTestApplicationTheme {
        CreateListScreen(
            isModify = false,
            title = "",
            setTitle = {},
            emoji = "",
            setEmoji = {},
            onAddMoviesClick = {},
            onSaveListClick = { _, _, _ -> },
            resetSelectedMovies = {},
            addCategoryState = null,
            resetAddCategoryState = {},
            navController = NavHostController(context = LocalContext.current),
            removeMovie = {},
        )
    }
}

@Preview
@Composable
private fun MyNewListItemEmptyPreview() {
    EmergeTestApplicationTheme {
        MyNewListItem(
            isModify = false,
            emoji = "",
            title = "My Favourite western movies",
            onAddMoviesClick = {},
            onSaveListClick = {},
            selectedMovies = emptyList(),
            removeMovie = {},
        )
    }
}
