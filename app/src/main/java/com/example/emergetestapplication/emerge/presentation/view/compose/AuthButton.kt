package com.example.emergetestapplication.emerge.presentation.view.compose

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.arun.emergetestapplication.R

@Composable
fun AuthButton(
    onClick: () -> Unit,
    text: String,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors =
            ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.teal_700),
                contentColor = Color.White,
            ),
        modifier = modifier,
        enabled = isEnabled,
    ) {
        Text(text)
    }
}
