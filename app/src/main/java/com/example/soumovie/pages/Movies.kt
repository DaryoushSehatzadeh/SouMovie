package com.example.soumovie.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.soumovie.R
import com.example.soumovie.viewmodel.MovieViewModel
import com.example.soumovie.data.Result
import androidx.compose.foundation.shape.RoundedCornerShape


@Composable
fun Movies(navController: NavHostController) {
    // Get the ViewModel
    val movieViewModel: MovieViewModel = viewModel()

    // Observe the LiveData from the ViewModel
    val popularMovies = movieViewModel.popularMovies.value
    val allMovies = movieViewModel.allMovies.value // All Movies LiveData

    // Load the movies when the composable first launches
    LaunchedEffect(Unit) {
        movieViewModel.loadPopularMovies()
        movieViewModel.loadAllMovies()  // Load all movies
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // **Logo**
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(300.dp)
                .padding(bottom = 32.dp)
        )

        // Display loading spinner if data is being fetched
        if (popularMovies == null || allMovies == null) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            // Display Top 10 Popular Movies
            Text(
                text = "Top 10 Popular Movies",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )

            // Display popular movies in a LazyColumn
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(popularMovies.take(10)) { movie ->
                    MovieItem(movie = movie)
                }
            }

            // Add some space between the two lists
            Spacer(modifier = Modifier.height(16.dp))

            // Display All Movies (capped at 100)
            Text(
                text = "All Movies",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(allMovies.take(100)) { movie ->
                    MovieItem(movie = movie)
                }
            }
        }
    }
}

@Composable
fun MovieItem(movie: Result) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.Gray, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
    ) {
        // Movie poster image
        Image(
            painter = painterResource(id = R.drawable.movie_placeholder), // Replace with actual movie poster if available
            contentDescription = movie.title,
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Movie title
        Column {
            Text(text = movie.title, color = Color.White, fontSize = 16.sp)
            Text(
                text = "Rating: ${movie.voteAverage}",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
        }
    }
}
