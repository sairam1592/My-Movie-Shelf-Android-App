package com.example.emergetestapplication.emerge.presentation.view.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arun.emergetestapplication.R
import com.example.emergetestapplication.emerge.data.model.movies.Movie
import com.example.emergetestapplication.ui.theme.EmergeTestApplicationTheme

@Composable
fun MyNewListItem(
    isModify: Boolean,
    emoji: String,
    title: String,
    onAddMoviesClick: () -> Unit,
    onSaveListClick: () -> Unit,
    selectedMovies: List<Movie>,
    removeMovie: (Movie) -> Unit,
) {
    val remainingMovies = 5 - selectedMovies.size

    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = colorResource(id = R.color.teal_700),
        modifier =
            Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "$emoji  ${title.uppercase()}",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                modifier =
                    Modifier
                        .border(1.dp, Color.White, RoundedCornerShape(8.dp))
                        .padding(horizontal = 15.dp, vertical = 10.dp)
                        .align(Alignment.CenterHorizontally),
            )

            if (selectedMovies.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))

                selectedMovies.let { movies ->
                    LazyColumn(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .heightIn(max = 400.dp),
                    ) {
                        items(movies) { movie ->
                            SearchResultItem(showDeleteIcon = true, movie = movie, onClick = {}, removeMovie = removeMovie)
                        }
                    }
                }

                if (remainingMovies > 0) {
                    Text(
                        text = "Add $remainingMovies more ${if (remainingMovies == 1) "movie" else "movies"} to save the ${if (isModify) "modified" else ""} list...",
                        color = Color.White,
                        fontSize = 14.sp,
                        maxLines = 2,
                        modifier =
                            Modifier
                                .padding(top = 12.dp)
                                .align(Alignment.CenterHorizontally),
                    )
                }
            } else {
                Text(
                    text = stringResource(id = R.string.add_5_movies_subtitle),
                    color = Color.White,
                    fontSize = 14.sp,
                    maxLines = 2,
                    modifier =
                        Modifier
                            .padding(top = 18.dp)
                            .align(Alignment.CenterHorizontally),
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Button(
                    onClick = onAddMoviesClick,
                    shape = RoundedCornerShape(16.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = colorResource(id = R.color.teal_700),
                        ),
                    enabled = selectedMovies.size < 5,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "Add Movies",
                        tint = colorResource(id = R.color.teal_700),
                        modifier = Modifier.size(20.dp),
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(id = R.string.btn_add_movies))
                }

                Button(
                    onClick = { onSaveListClick() },
                    shape = RoundedCornerShape(16.dp),
                    colors =
                        ButtonDefaults.buttonColors(
                            backgroundColor = Color.White,
                            contentColor = colorResource(id = R.color.teal_700),
                        ),
                    modifier = Modifier.padding(start = 12.dp),
                    enabled = selectedMovies.size == 5,
                ) {
                    Text(text = stringResource(id = R.string.btn_save_list))
                }
            }
        }
    }
}

@Preview
@Composable
private fun MyNewListItemWithMovieInfoPreview() {
    val dummyMovies =
        listOf(
            Movie(
                id = 1,
                title = "The Good, the Bad and the Ugly",
                overview = "Test description 1",
                poster_path = "/mCU60YrUli3VfPVPOMDg26BgdhR.jpg",
                release_date = "",
                vote_average = 0.0,
            ),
            Movie(
                id = 2,
                title = "Once Upon a Time in the West",
                overview = "Test Description 2",
                poster_path = "/mCU60YrUli3VfPVPOMDg26BgdhR.jpg",
                release_date = "",
                vote_average = 0.0,
            ),
        )

    EmergeTestApplicationTheme {
        MyNewListItem(
            isModify = true,
            emoji = "",
            title = "My Favourite western movies",
            onAddMoviesClick = {},
            onSaveListClick = {},
            selectedMovies = dummyMovies,
            removeMovie = {},
        )
    }
}
