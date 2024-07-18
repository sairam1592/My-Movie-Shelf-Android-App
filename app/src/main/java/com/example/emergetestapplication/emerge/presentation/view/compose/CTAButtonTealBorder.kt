package com.example.emergetestapplication.emerge.presentation.view.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arun.emergetestapplication.R
import com.example.emergetestapplication.ui.theme.EmergeTestApplicationTheme

@Composable
fun CTAButtonTealBorder(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors =
            ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = Color.Black,
            ),
        border = BorderStroke(2.dp, color = colorResource(id = R.color.teal_700)),
        onClick = onClick,
    ) {
        Text(text = text, color = Color.Black)
    }
}

@Composable
@Preview
private fun CTAButtonTealBorderPreview() {
    EmergeTestApplicationTheme {
        CTAButtonTealBorder(
            text = "Create A List",
            onClick = {},
        )
    }
}
