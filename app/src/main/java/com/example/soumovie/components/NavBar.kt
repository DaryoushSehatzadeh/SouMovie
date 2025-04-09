package com.example.soumovie.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun NavBar(navController: NavController, currentPage: String) {
    BottomAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            NavBarItem(
                icon = Icons.Filled.Home,
                description = "Movies",
                destination = "Movies",
                navController = navController,
                currentPage = currentPage
            )
            NavBarItem(
                icon = Icons.Filled.Search,
                description = "SearchMovies",
                destination = "SearchMovies",
                navController = navController,
                currentPage = currentPage
            )
            NavBarItem(
                icon = Icons.Filled.Favorite,
                description = "Watchlist",
                destination = "watchlist",
                navController = navController,
                currentPage = currentPage
            )
        }
    }
}

@Composable
fun NavBarItem(
    icon: ImageVector,
    description: String,
    destination: String,
    navController: NavController,
    currentPage: String
) {
    val isSelected = destination == currentPage
    IconButton(
        onClick = {
            if (!isSelected) {
                navController.navigate(destination)
            }
        },
        enabled = !isSelected
    ) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            tint = if (isSelected) Color.Gray else Color.White
        )
    }
}