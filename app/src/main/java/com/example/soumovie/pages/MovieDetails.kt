package com.example.soumovie.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.soumovie.data.MovieDetails
import com.example.soumovie.viewmodel.MovieViewModel


@Composable
fun MovieDetails(movieId: Int) {
    val movieViewModel: MovieViewModel = viewModel()

    // Observe movie details
    val movieDetails by movieViewModel.movieDetails.observeAsState()

    var description by remember { mutableStateOf("") }

    // Fetch movie details when the screen is first displayed
    LaunchedEffect(movieId) {
        movieViewModel.loadMovieDetails(movieId)
    }

    // Update description when movieDetails is populated
    LaunchedEffect(movieDetails) {
        movieDetails?.let {
            description = it.overview
        }
    }

    // Background color
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {

        // Display loading spinner while data is being fetched
        if (movieDetails == null) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            // Use movieDetails to display information
            movieDetails?.let { movie ->
                // Banner Image
                Image(
                    painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500/${movieDetails?.backdropPath}"),
                    contentDescription = "Movie Banner",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .align(Alignment.TopCenter)
                        .offset(y = 100.dp),
                    contentScale = ContentScale.Crop
                )

                // Poster Image overlaid on top of the banner
                Image(
                    painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500/${movieDetails?.posterPath}"),
                    contentDescription = "Movie Poster",
                    modifier = Modifier
                        .width(120.dp)
                        .height(200.dp)
                        .align(Alignment.TopCenter)
                        .offset(y = 200.dp)
                        .offset(x = (-100).dp),
                    contentScale = ContentScale.Crop
                )

                // Movie Title
                Text(
                    text = movie.title,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(75.dp,(-85).dp)
                        .width(200.dp),
                    style = MaterialTheme.typography.titleLarge
                )

                // Release data and run time
                Text(
                    text = "Release Year: ${movie.releaseDate.take(4)} \t\t\t\t" +
                            "Runtime: ${if (movie.runtime > 0) "${movie.runtime} minutes" else "Not Listed"}",
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .offset(y = 50.dp),
                    style = MaterialTheme.typography.titleMedium
                )

                // Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(50.dp)
                        .align(Alignment.Center)
                        .offset(y = 100.dp),
                ) {
                    Text(
                        text = "Description",
                        modifier = Modifier
                            .offset(x = (-25).dp)
                            .clickable{
                                description = movie.overview
                            },
                        color = Color.White
                    )

                    Text(
                        text = "Reviews",
                        modifier = Modifier
                            .offset(x = 20.dp)
                            .clickable{
                            },
                        color = Color.White
                    )

                    Text(
                        text = "Cast",
                        modifier = Modifier
                            .offset(x = 80.dp)
                            .clickable{

                            },
                        color = Color.White
                    )
                }
                // Text box for description, etc
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .align(Alignment.BottomCenter)
                        .offset( y = (-10).dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width((LocalConfiguration.current.screenWidthDp * 0.9).dp)
                            .height(200.dp) // Adjust size as needed
                            .background(Color.White)
                            .padding(16.dp) // Padding for the content inside
                            .align(Alignment.CenterHorizontally)
                    ){
                        Column(
                            modifier = Modifier.verticalScroll(rememberScrollState()) // Enable vertical scroll
                        ) {
                            Text(
                                    text = description,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                        }
                    }
                }
            }
        }
    }
}
