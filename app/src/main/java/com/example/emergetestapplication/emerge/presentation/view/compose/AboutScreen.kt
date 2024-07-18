package com.example.emergetestapplication.emerge.presentation.view.compose

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.arun.emergetestapplication.R
import com.example.emergetestapplication.ui.theme.EmergeTestApplicationTheme
import kotlinx.coroutines.launch

@Composable
fun AboutScreen(
    navController: NavHostController,
    onDeleteAccount: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "About",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                        )
                    }
                },
                backgroundColor = colorResource(id = R.color.teal_700),
            )
        },
        content = { paddingValues ->
            Column(
                modifier =
                Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                    .padding(paddingValues = PaddingValues(16.dp))
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    backgroundColor = colorResource(id = R.color.teal_700),
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Technologies Used",
                            style =
                                MaterialTheme.typography.h6.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                ),
                            color = Color.White,
                            modifier = Modifier.padding(bottom = 10.dp),
                        )
                        Text(
                            color = Color.White,
                            text =
                                "Kotlin\n" +
                                    "Jetpack Compose\n" +
                                    "Kotlin Coroutines\n" +
                                    "Kotlin States\n" +
                                    "Kotlin Flow\n" +
                                    "MVVM Architecture\n" +
                                    "Firebase Firestore DB\n" +
                                    "TMDB API\n" +
                                    "Signup/Login/Logout with Room DB",
                            style = MaterialTheme.typography.body1.copy(fontSize = 16.sp),
                        )
                    }
                }

                Card(
                    shape = RoundedCornerShape(16.dp),
                    backgroundColor = colorResource(id = R.color.teal_700),
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            color = Color.White,
                            text = "How to Test the App",
                            style =
                                MaterialTheme.typography.h6.copy(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                ),
                            modifier = Modifier.padding(bottom = 16.dp),
                        )
                        Text(
                            color = Color.White,
                            text =
                                "1. Create a List\n" +
                                    "2. Open the Search Screen to add Movies from TMDB API\n" +
                                    "3. Add or remove movies in a list\n" +
                                    "4. Delete an entire list if not required\n" +
                                    "5. Search other user accounts and view their top movie lists\n" +
                                    "6. All lists are stored remotely in Firebase Firestore",
                            style = MaterialTheme.typography.body1.copy(fontSize = 16.sp),
                        )
                    }
                }

                CTAButtonTealBorder(
                    text = stringResource(id = R.string.delete_account),
                    onClick = onDeleteAccount,
                    modifier =
                        Modifier
                            .wrapContentSize()
                            .padding(top = 30.dp),
                        )
            }
        },
    )

    // Handle back button press
    BackHandler {
        coroutineScope.launch {
            navController.popBackStack()
        }
    }
}

@Preview
@Composable
fun AboutScreenPreview() {
    EmergeTestApplicationTheme {
        AboutScreen(
            navController = NavHostController(context = LocalContext.current),
            onDeleteAccount = {})
    }
}
