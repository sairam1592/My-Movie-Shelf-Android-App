package com.example.emergetestapplication.emerge.presentation.view.compose

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.emergetestapplication.R
import com.example.emergetestapplication.emerge.presentation.view.state.AuthState
import com.example.emergetestapplication.emerge.presentation.view.state.MoviesState
import com.example.emergetestapplication.ui.theme.EmergeTestApplicationTheme

@Composable
fun HomeScreen(
    authState: AuthState,
    moviesState: MoviesState,
    onLogout: () -> Unit,
    onLogoutSuccess: () -> Unit,
    onGetPopularMovies: () -> Unit,
) {
    val context = LocalContext.current
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .background(color = colorResource(id = R.color.white))
                    .fillMaxSize()
                    .padding(16.dp)
                    .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Welcome!", style = MaterialTheme.typography.h5)

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                modifier =
                    Modifier
                        .wrapContentSize(),
                shape = RoundedCornerShape(16.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.teal_700),
                        contentColor = Color.White,
                    ),
                onClick = onGetPopularMovies,
            ) {
                Text(text = "Get Popular Movies")
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (moviesState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
            } else if (moviesState.errorMessage != null) {
                Text(text = "Error: ${moviesState.errorMessage}")
            } else {
                LazyColumn {
                    items(moviesState.movies) { movie ->
                        Text(modifier = Modifier.padding(10.dp), text = movie.title)
                    }
                }
            }
        }

        Button(
            modifier =
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(16.dp),
            colors =
                ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.teal_700),
                    contentColor = Color.White,
                ),
            onClick = {
                onLogout()
                Toast.makeText(context, "Logout Successful", Toast.LENGTH_SHORT).show()
            },
        ) {
            Text("Logout", color = Color.White)
        }

        if (!authState.isAuthenticated) {
            onLogoutSuccess()
        }
        if (authState.errorMessage != null) {
            Toast.makeText(context, authState.errorMessage, Toast.LENGTH_SHORT).show()
            Text(text = authState.errorMessage, color = Color.Red)
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    EmergeTestApplicationTheme {
        HomeScreen(
            authState = AuthState(),
            moviesState = MoviesState(),
            onLogout = {},
            onLogoutSuccess = {},
            onGetPopularMovies = {},
        )
    }
}
