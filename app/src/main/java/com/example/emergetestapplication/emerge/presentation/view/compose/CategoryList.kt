package com.example.emergetestapplication.emerge.presentation.view.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.emergetestapplication.emerge.data.model.firebase.FbCategoryModel
import com.example.emergetestapplication.emerge.data.model.firebase.FbMovieModel
import com.example.emergetestapplication.ui.theme.EmergeTestApplicationTheme

@Composable
fun CategoryList(
    categories: List<FbCategoryModel>,
    onDeleteCategory: (FbCategoryModel) -> Unit,
    onModifyCategory: (FbCategoryModel) -> Unit,
    isDeleteCategoryEnabled: Boolean,
    isShowModifyButton: Boolean
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        items(categories) { category ->
            CategoryItem(
                category = category,
                onDeleteCategory = onDeleteCategory,
                onModifyCategory = onModifyCategory,
                isDeleteCategoryEnabled = isDeleteCategoryEnabled,
                isShowModifyButton = isShowModifyButton
            )
        }
    }
}

@Preview
@Composable
private fun CategoryListPreview() {
    EmergeTestApplicationTheme {
        CategoryList(
            onDeleteCategory = {},
            onModifyCategory = { _-> },
            isDeleteCategoryEnabled = true,
            isShowModifyButton = true,
            categories =
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
        )
    }
}