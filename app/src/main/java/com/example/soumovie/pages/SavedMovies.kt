package com.example.soumovie.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.soumovie.components.NavBar
import com.example.soumovie.data.SavedMovie
import com.example.soumovie.repository.SavedMovieRepository
import com.example.soumovie.viewmodel.SavedMoviesViewModel

@Composable
fun SavedMovies(navController: NavHostController, repository: SavedMovieRepository) {

    val viewModel = remember {
        SavedMoviesViewModel(repository)
    }

    val watchlist by viewModel.getWatchlist().observeAsState(emptyList())

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp)
        ) {
            Text(
                text = "My Watchlist",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )

            if (watchlist.isEmpty()) {
                Text(
                    text = "No movies added yet.",
                    color = Color.White,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn {
                    items(watchlist) { movie ->
                        WatchlistItem(movie, navController, viewModel)
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .align(Alignment.BottomCenter)
        ) {
            NavBar(navController, "Watchlist")
        }
    }
}

@Composable
fun WatchlistItem(movie: SavedMovie, navController: NavController, viewModel: SavedMoviesViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.DarkGray)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = movie.name,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )

        Button(onClick = { navController.navigate("movieDetails/${movie.id}") }) {
            Text("View")
        }

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = { viewModel.deleteMovie(movie) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        )
        {
            Text("Remove")
        }
    }
}
