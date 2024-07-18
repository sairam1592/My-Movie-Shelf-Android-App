package com.example.emergetestapplication.emerge.presentation.view.compose

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arun.emergetestapplication.R
import com.example.emergetestapplication.emerge.common.AppConstants
import com.example.emergetestapplication.emerge.presentation.view.state.AuthState
import com.example.emergetestapplication.ui.theme.EmergeTestApplicationTheme

@Composable
fun SignUpScreen(
    authState: AuthState,
    onSignUp: (String, String, () -> Unit, () -> Unit) -> Unit,
    onSignUpSuccess: () -> Unit,
    onAccountExists: () -> Unit,
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier =
            Modifier
                .background(color = colorResource(id = R.color.white))
                .fillMaxSize()
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(id = R.string.signup_title),
            color = Color.Black,
            fontSize = 20.sp,
            maxLines = 1,
        )

        Text(
            modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
            text = stringResource(id = R.string.signup_desc),
            color = Color.Red,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
        )

        AuthTextField(
            value = username,
            onValueChange = { username = it },
            label = stringResource(id = R.string.hint_username),
            modifier = Modifier.padding(top = 30.dp),
        )
        AuthTextField(
            value = password,
            onValueChange = { password = it },
            label = stringResource(id = R.string.hint_password),
            isPassword = true,
            modifier = Modifier.padding(top = 25.dp),
        )
        AuthButton(
            onClick = {
                onSignUp(username, password, {
                    Toast
                        .makeText(context, AppConstants.ACCOUNT_ALREADY_EXISTS, Toast.LENGTH_SHORT)
                        .show()
                    onAccountExists()
                }, {
                    Toast
                        .makeText(context, AppConstants.TOAST_SIGNUP_SUCCESS, Toast.LENGTH_SHORT)
                        .show()
                    onSignUpSuccess()
                })
            },
            text = stringResource(id = R.string.btn_signup),
            isEnabled = username.isNotBlank() && password.isNotBlank(),
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
        )

        if (authState.isLoading) {
            CircularProgressIndicator(color = colorResource(id = R.color.teal_700))
        }
        if (authState.isAuthenticated) {
            Toast.makeText(context, AppConstants.TOAST_SIGNUP_SUCCESS, Toast.LENGTH_SHORT).show()
            onSignUpSuccess()
        }
    }
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    EmergeTestApplicationTheme {
        SignUpScreen(
            authState = AuthState(),
            onSignUp = { _, _, _, _ -> },
            onSignUpSuccess = { },
            onAccountExists = { },
        )
    }
}
