package com.example.soumovie.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
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

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp, top = 35.dp)
        ) {
            Text(
                text = "My Watchlist",
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )

            if (watchlist.isEmpty()) {
                Text(
                    text = "No movies added yet.",
                    color = MaterialTheme.colorScheme.onPrimary,
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
            NavBar(navController, "SavedMovies")
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun WatchlistItem(movie: SavedMovie, navController: NavController, viewModel: SavedMoviesViewModel) {
    Row(
        modifier = Modifier
            .clickable { navController.navigate("movieDetails/${movie.movieId}") }
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(80.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w500/${movie.posterPath}")
                    .crossfade(true)
                    .build(),
                contentDescription = "Movie Poster",
                modifier = Modifier.fillMaxSize(),
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column (modifier = Modifier.width(140.dp)){
            Text(text = movie.name, color = MaterialTheme.colorScheme.onPrimary, fontSize = 16.sp)
            Text(
                text = "Rating: ${String.format("%.1f", movie.rating)}",
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.width(32.dp))

        IconButton(
            onClick = { viewModel.deleteMovie(movie) }
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.Red
            )
        }
    }
}
