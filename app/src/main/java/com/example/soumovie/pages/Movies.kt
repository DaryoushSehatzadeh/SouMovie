package com.example.soumovie.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.soumovie.components.NavBar
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
    val allMovies = all?.drop(10)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (-100).dp)
                .background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // **Logo**
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(250.dp)
                    .padding(top = 125.dp)
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
                    modifier = Modifier.padding(8.dp)
                )

                // Display popular movies in a LazyColumn
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    items(popularMovies ?: emptyList()) { movie ->  // Ensure allMovies is a List
                        MovieItem(
                            movie = movie,
                            navController = navController
                        )  // Passing MovieResult object
                    }
                }

                // Add some space between the two lists
                Spacer(modifier = Modifier.height(8.dp))

                // Display All Movies (capped at 100)
                Text(
                    text = "All Movies",
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp)
                )

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(allMovies ?: emptyList()) { movie ->  // Ensure allMovies is a List
                        MovieItem(
                            movie = movie,
                            navController = navController
                        )  // Passing MovieResult object
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .align(Alignment.BottomCenter)
        ){
            NavBar(navController, "Movies")
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun MovieItem(movie: Result, navController: NavHostController) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.Gray, shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
            .clickable {
                // Navigate to movie details and pass the movie id
                navController.navigate("movieDetails/${movie.id}")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Movie poster image
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(Color.White)  // Set the background color to white
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://image.tmdb.org/t/p/w500/${movie.posterPath}")  // Construct the URL
                    .crossfade(true)
                    .build(),
                contentDescription = "Movie Poster",
                modifier = Modifier.fillMaxSize(),  // Fill the Box size with the image
            )
        }


        Spacer(modifier = Modifier.width(16.dp))

        // Movie title
        Column {
            Text(text = movie.title, color = Color.White, fontSize = 16.sp)
            Text(
                text = "Rating: ${String.format("%.1f", movie.voteAverage)}",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
        }
    }
}
