package com.example.emergetestapplication.emerge.presentation.view.compose

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arun.emergetestapplication.R
import com.example.emergetestapplication.ui.theme.EmergeTestApplicationTheme

@Composable
fun SearchTextField(
    query: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        label = { Text(label) },
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
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
    )
}

@Preview
@Composable
private fun SearchTextFieldPreview() {
    EmergeTestApplicationTheme {
        SearchTextField(
            query = TextFieldValue(""),
            onQueryChange = {},
            label = "Search by UserName",
        )
    }
}
