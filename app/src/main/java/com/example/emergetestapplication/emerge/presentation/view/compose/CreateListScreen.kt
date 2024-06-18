package com.example.emergetestapplication.emerge.presentation.view.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emergetestapplication.R
import com.example.emergetestapplication.ui.theme.EmergeTestApplicationTheme

@Composable
fun CreateListScreen(onAddMoviesClick: () -> Unit) {
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var emoji by remember { mutableStateOf(TextFieldValue("")) }
    var isListCreated by remember { mutableStateOf(false) }

    Column(
        modifier =
            Modifier
                .background(color = colorResource(id = R.color.white))
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Text(text = "Create A List", style = MaterialTheme.typography.h6, fontSize = 24.sp)

        Spacer(modifier = Modifier.height(20.dp))

        if (!isListCreated) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title of the list") },
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
                onValueChange = { emoji = it },
                label = { Text("Choose an Emoji") },
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
            ) {
                Text(text = "Create")
            }
        } else {
            MovieListItem(
                emoji = emoji.text,
                title = title.text,
                onAddMoviesClick = onAddMoviesClick,
            )
        }
    }
}

@Composable
fun MovieListItem(
    emoji: String,
    title: String,
    onAddMoviesClick: () -> Unit,
) {
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
                text = "$emoji $title",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                modifier =
                    Modifier
                        .border(2.dp, Color.White, RoundedCornerShape(8.dp))
                        .padding(horizontal = 15.dp, vertical = 10.dp)
                        .align(Alignment.CenterHorizontally),
            )

            Text(
                text = "Add your Favourite Top 5 Movies Here...",
                color = Color.White,
                fontSize = 14.sp,
                maxLines = 2,
                modifier =
                    Modifier
                        .padding(top = 18.dp)
                        .align(Alignment.CenterHorizontally),
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = onAddMoviesClick,
                shape = RoundedCornerShape(16.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = colorResource(id = R.color.teal_700),
                    ),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add), // Ensure you have a plus icon drawable
                    contentDescription = "Add Movies",
                    tint = colorResource(id = R.color.teal_700),
                    modifier = Modifier.size(24.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Movies")
            }
        }
    }
}

@Preview
@Composable
private fun CreateListScreenPreview() {
    EmergeTestApplicationTheme {
        CreateListScreen(onAddMoviesClick = {})
    }
}

@Preview
@Composable
private fun MovieListItemPreview() {
    EmergeTestApplicationTheme {
        MovieListItem(emoji = "", title = "My Favourite western movies", onAddMoviesClick = {})
    }
}
