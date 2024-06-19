package com.example.emergetestapplication.emerge.presentation.view.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emergetestapplication.R
import com.example.emergetestapplication.emerge.data.model.firebase.FbCategoryModel
import com.example.emergetestapplication.emerge.data.model.firebase.FbMovieModel
import com.example.emergetestapplication.emerge.domain.mapper.MovieMapper.fromFbModelList
import com.example.emergetestapplication.ui.theme.EmergeTestApplicationTheme

@Composable
fun CategoryItem(
    category: FbCategoryModel,
    onDeleteCategory: (FbCategoryModel) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Delete Category") },
            text = { Text("Are you sure you want to delete this category?") },
            confirmButton = {
                TextButton(
                    colors =
                    ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.teal_700),
                        contentColor = Color.White,
                    ),
                    onClick = {
                        onDeleteCategory(category)
                        showDialog = false
                    },
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    colors =
                    ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.teal_700),
                        contentColor = Color.White,
                    ),
                    onClick = { showDialog = false },
                ) {
                    Text("Cancel")
                }
            },
        )
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = colorResource(id = R.color.teal_700),
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        showDialog = true
                    },
                )
            },
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
        ) {
            Text(
                text = "${category.emoji} ${category.title}",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                modifier =
                Modifier
                    .border(1.dp, Color.White, RoundedCornerShape(8.dp))
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .align(Alignment.CenterHorizontally),
            )

            Spacer(modifier = Modifier.height(8.dp))

            fromFbModelList(category.movies).sortedBy { it.title }.takeIf { it.isNotEmpty() }?.let { movies ->
                LazyColumn(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .heightIn(max = 185.dp),
                ) {
                    items(movies) { movie ->
                        MovieItem(movie = movie)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Preview
@Composable
private fun CategoryItemPreview() {
    EmergeTestApplicationTheme {
        CategoryItem(
            onDeleteCategory = {},
            category =
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
        )
    }
}