package com.example.emergetestapplication.emerge.presentation.view.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.arun.emergetestapplication.R
import com.example.emergetestapplication.emerge.data.model.movies.Movie

@Composable
fun MovieItem(movie: Movie) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = colorResource(id = R.color.teal_700),
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .border(0.2.dp, Color.White, RoundedCornerShape(4)),
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
                        .size(30.dp)
                        .padding(4.dp),
                shape = RoundedCornerShape(4.dp),
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
                            .clip(RoundedCornerShape(4.dp)),
                    contentScale = ContentScale.Crop,
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = movie.title,
                style = MaterialTheme.typography.body1,
                maxLines = 1,
                color = Color.White,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}
