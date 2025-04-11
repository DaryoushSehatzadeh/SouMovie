package com.example.soumovie.pages


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.soumovie.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Splash Image",
            modifier = Modifier.fillMaxSize()
        )
    }

    // 2 Second Delay Then Display T&C
    LaunchedEffect(Unit) {
        delay(2000)

        // After Delay, Display T&C
        navController.navigate("tnc")
    }
}
