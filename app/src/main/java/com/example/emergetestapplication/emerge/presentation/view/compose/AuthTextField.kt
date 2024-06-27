package com.example.emergetestapplication.emerge.presentation.view.compose

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arun.emergetestapplication.R

@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    modifier: Modifier = Modifier,
) {
    var passwordVisibility by remember { mutableStateOf(false) }
    val visualTransformation =
        if (isPassword && !passwordVisibility) PasswordVisualTransformation() else VisualTransformation.None

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        shape = RoundedCornerShape(4.dp),
        visualTransformation = visualTransformation,
        colors =
            TextFieldDefaults.textFieldColors(
                textColor = colorResource(id = R.color.black),
                focusedIndicatorColor = colorResource(id = R.color.teal_700),
                cursorColor = colorResource(id = R.color.teal_700),
                focusedLabelColor = colorResource(id = R.color.teal_700),
                unfocusedLabelColor = colorResource(id = R.color.teal_700),
            ),
        trailingIcon = {
            if (isPassword) {
                val image =
                    if (passwordVisibility) {
                        Icons.Filled.Create
                    } else {
                        Icons.Filled.Create
                    }

                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            }
        },
        modifier = modifier,
    )
}

@Composable
@Preview
private fun AuthTextFieldPreview() {
    AuthTextField(
        value = "",
        onValueChange = {},
        label = "Password",
        isPassword = true,
    )
}
