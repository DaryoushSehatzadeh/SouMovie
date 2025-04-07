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
import com.example.soumovie.viewmodel.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.soumovie.data.Result
import com.example.soumovie.model.*

@Composable
fun Movies(navController: NavHostController) {
    // Get the ViewModel
    val movieViewModel: MovieViewModel = viewModel()

    // Observe the LiveData from the ViewModel
    val popular by movieViewModel.popularMovies.observeAsState(emptyList())
    val all by movieViewModel.allMovies.observeAsState(emptyList())

    // Load the movies when the composable first launches
    LaunchedEffect(Unit) {
        movieViewModel.loadPopularMovies()
        movieViewModel.loadAllMovies()
    }

    val popularMovies = popular?.take(10)
    val allMovies = all?.take(100)

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
        if (popularMovies?.isEmpty() == true || allMovies?.isEmpty() == true) {
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
                items(popularMovies ?: emptyList()) { movie ->  // Ensure allMovies is a List
                    MovieItem(movie = movie)  // Passing MovieResult object
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
                items(allMovies ?: emptyList()) { movie ->  // Ensure allMovies is a List
                    MovieItem(movie = movie)  // Passing MovieResult object
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
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color.White)  // Set the background color to white
        ) {
            // Optionally, add content inside the Box if needed
        }


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
