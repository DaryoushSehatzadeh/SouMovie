package com.example.soumovie.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.soumovie.R

@Composable
fun LandingPage(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black), // Black background
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Centers content vertically
    ) {
        // **Logo**
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(300.dp) // Adjust size as needed
                .padding(bottom = 32.dp) // Space between logo and buttons
        )

        // **Movies Button**
        Button(
            onClick = { navController.navigate("movies") },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(16.dp)
                .height(60.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Movies", color = Color.White, fontSize = 20.sp)
        }

        // **Watchlist Button**
        Button(
            onClick = { navController.navigate("watchlist") },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(16.dp)
                .height(60.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Watchlist", color = Color.White, fontSize = 20.sp)
        }
    }
}
