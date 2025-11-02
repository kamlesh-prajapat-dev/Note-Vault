package com.example.notevault.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.notevault.R
import com.example.notevault.ui.NotesAppScreen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = viewModel(
        factory = SplashViewModel.Factory
    ),
    navController: NavHostController
) {

    val isToken by viewModel.isToken.collectAsState()

    // Navigate based on token after showing splash
    LaunchedEffect(isToken) {
        delay(2000)
        if (isToken) {
            navController.navigate(NotesAppScreen.NotesList.name) {
                popUpTo(NotesAppScreen.SPLASH.name) { inclusive = true }
            }
        } else {
            navController.navigate(NotesAppScreen.Login.name) {
                popUpTo(NotesAppScreen.SPLASH.name) { inclusive = true }
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.ic_person), // your app logo
                contentDescription = "App Logo",
                modifier = Modifier.size(120.dp),
            )
        }
    }
}