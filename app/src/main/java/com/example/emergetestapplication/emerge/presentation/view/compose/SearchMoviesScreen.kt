package com.example.emergetestapplication.emerge.presentation.view.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.emergetestapplication.R
import com.example.emergetestapplication.emerge.data.model.movies.Movie
import com.example.emergetestapplication.emerge.presentation.view.state.MoviesState
import com.example.emergetestapplication.ui.theme.EmergeTestApplicationTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchMoviesScreen(
    moviesState: MoviesState,
    searchMovies: (String) -> Unit,
    onMovieSelected: (Movie) -> Unit,
    clearMoviesSearch: () -> Unit,
) {
    var query by remember { mutableStateOf(TextFieldValue("")) }
    var searchJob by remember { mutableStateOf<Job?>(null) }
    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        onDispose {
            query = TextFieldValue("")
            clearMoviesSearch()
        }
    }

    Column(
        modifier =
            Modifier
                .background(color = colorResource(id = R.color.white))
                .fillMaxSize()
                .padding(16.dp),
    ) {
        OutlinedTextField(
            colors =
                TextFieldDefaults.outlinedTextFieldColors(
                    textColor = colorResource(id = R.color.black),
                    backgroundColor = colorResource(id = R.color.white),
                    focusedBorderColor = colorResource(id = R.color.teal_700),
                    unfocusedBorderColor = colorResource(id = R.color.teal_700),
                    cursorColor = colorResource(id = R.color.teal_700),
                    focusedLabelColor = colorResource(id = R.color.teal_700),
                    unfocusedLabelColor = colorResource(id = R.color.teal_700),
                ),
            shape = RoundedCornerShape(16.dp),
            value = query,
            onValueChange = {
                query = it
                searchJob?.cancel()
                searchJob =
                    coroutineScope.launch {
                        delay(500)
                        if (query.text.isNotEmpty()) {
                            searchMovies(query.text)
                        }
                    }
            },
            label = { Text("Search A Movie to Add to Your List") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        when {
            moviesState.isLoading -> {
                CircularProgressIndicator(
                    modifier =
                        Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(24.dp)
                            .align(Alignment.CenterHorizontally),
                    color = colorResource(id = R.color.teal_700),
                )
            }

            moviesState.errorMessage != null -> {
                Text(text = "Error: ${moviesState.errorMessage}")
            }

            else -> {
                LazyColumn {
                    items(moviesState.movies) { movie ->
                        MovieListItem(movie = movie, onClick = { onMovieSelected(movie) })
                    }
                }
            }
        }
    }
}

@Composable
fun MovieListItem(
    movie: Movie,
    onClick: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        backgroundColor = colorResource(id = R.color.teal_700),
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(0.2.dp, Color.White, RoundedCornerShape(4))
                .clickable { onClick() },
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Card(
                modifier =
                    Modifier
                        .size(40.dp),
                shape = RoundedCornerShape(8.dp),
            ) {
                SubcomposeAsyncImage(
                    model =
                        ImageRequest
                            .Builder(LocalContext.current)
                            .data(
                                data = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                            ).apply(
                                block = fun ImageRequest.Builder.() {
                                    placeholder(
                                        R.drawable.placeholder_dummy,
                                    )
                                    error(
                                        R.drawable.placeholder_dummy,
                                    )
                                },
                            ).build(),
                    contentDescription = "Program Icon",
                    modifier =
                        Modifier
                            .background(Color.Gray)
                            .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier =
                    Modifier
                        .padding(10.dp),
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.body1,
                    maxLines = 1,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.caption,
                    maxLines = 1,
                    color = Color.White,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Preview
@Composable
private fun SearchMoviesScreenPreview() {
    EmergeTestApplicationTheme {
        SearchMoviesScreen(
            moviesState = MoviesState(),
            searchMovies = {},
            onMovieSelected = {},
            clearMoviesSearch = {},
        )
    }
}

@Preview
@Composable
private fun MovieListItemPreview() {
    EmergeTestApplicationTheme {
        MovieListItem(
            movie =
                Movie(
                    id = 1,
                    title = "MoneyBall",
                    overview = "Great movie about Baseball and Statistics.",
                    poster_path = "/mCU60YrUli3VfPVPOMDg26BgdhR.jpg",
                    release_date = "",
                    vote_average = 0.0,
                ),
            onClick = {},
        )
    }
}
