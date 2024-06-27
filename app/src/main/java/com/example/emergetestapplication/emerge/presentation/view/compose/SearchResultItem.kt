package com.example.emergetestapplication.emerge.presentation.view.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.arun.emergetestapplication.R
import com.example.emergetestapplication.emerge.data.model.movies.Movie
import com.example.emergetestapplication.ui.theme.EmergeTestApplicationTheme

@Composable
fun SearchResultItem(
    showDeleteIcon: Boolean,
    movie: Movie,
    onClick: () -> Unit,
    removeMovie: (Movie) -> Unit,
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
            modifier =
                Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
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
                        .padding(8.dp)
                        .weight(1f),
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

            if (showDeleteIcon) {
                IconButton(onClick = { removeMovie(movie) }) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = "Remove Movie",
                        tint = Color.Red,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MovieListItemPreview() {
    EmergeTestApplicationTheme {
        SearchResultItem(
            showDeleteIcon = true,
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
            removeMovie = {},
        )
    }
}
