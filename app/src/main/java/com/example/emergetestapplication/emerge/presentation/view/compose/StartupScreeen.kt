package com.example.emergetestapplication.emerge.presentation.view.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.emergetestapplication.R
import com.example.emergetestapplication.ui.theme.EmergeTestApplicationTheme

@Composable
fun StartupScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToSignUp: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .background(color = colorResource(id = R.color.white))
                .fillMaxSize()
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Button(
            onClick = onNavigateToLogin,
            modifier =
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
        ) {
            Text("LOGIN")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onNavigateToSignUp,
            modifier =
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
        ) {
            Text("SIGNUP")
        }
    }
}

@Preview
@Composable
private fun StartupScreenPreview() {
    EmergeTestApplicationTheme {
        StartupScreen(onNavigateToLogin = { }) {
//
        }
    }
}
