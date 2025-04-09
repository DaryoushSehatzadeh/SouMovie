package com.example.soumovie.pages

import android.util.Log
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.soumovie.components.NavBar
import com.example.soumovie.data.MovieDetails
import com.example.soumovie.model.CastUIModel
import com.example.soumovie.model.ReviewUIModel
import com.example.soumovie.repository.SavedMovieRepository
import com.example.soumovie.viewmodel.MovieViewModel
import com.example.soumovie.viewmodel.SavedMoviesViewModel

enum class ActiveTab {
    DESCRIPTION, CAST, REVIEWS
}

@Composable
fun MovieDetails(movieId: Int, navController: NavController, repository: SavedMovieRepository) {

    val viewModel = remember {
        SavedMoviesViewModel(repository)
    }

    val watchlist by viewModel.getWatchlist().observeAsState(emptyList())

    val movieViewModel: MovieViewModel = viewModel()

    // Observe movie details
    val movieDetails by movieViewModel.movieDetails.observeAsState()
    val castDetails by movieViewModel.castDetails.observeAsState()
    val reviewDetails by movieViewModel.reviewDetails.observeAsState()

    var description by remember { mutableStateOf("") }
    var cast by remember { mutableStateOf<List<CastUIModel>>(emptyList()) }
    var reviews by remember { mutableStateOf<List<ReviewUIModel>>(emptyList()) }

    var activeTab by remember { mutableStateOf(ActiveTab.DESCRIPTION) }

    // Fetch movie details when the screen is first displayed
    LaunchedEffect(movieId) {
        movieViewModel.loadMovieDetails(movieId)
        movieViewModel.loadCastDetails(movieId)
        movieViewModel.loadReviewDetails(movieId)
    }

    // Update description when movieDetails is populated
    LaunchedEffect(movieDetails) {
        movieDetails?.let {
            description = it.overview
        }
    }

    // Update cast when castDetails is populated
    LaunchedEffect(castDetails) {
        castDetails?.cast?.let { actor ->
            cast = actor.map {
                CastUIModel(
                    actorName = it.name,
                    character = it.character,
                    profileImageUrl = "https://image.tmdb.org/t/p/w500${it.profilePath}"
                )
            }
            Log.d("Reviews", "Loaded reviews: ${reviews.size}")
        }
    }

    // Update reviews when reviewsDetails is populated
    LaunchedEffect(reviewDetails) {
        Log.d("Reviews", "API Response: $reviewDetails")
        reviewDetails?.results?.let { data ->
            reviews = data.map {
                ReviewUIModel(
                    author = it.author,
                    content = it.content,
                )
            }
        }
    }

    // Background color
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(10f)
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .offset(y = 25.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.6f),
                        shape = CircleShape
                    )
                    .size(40.dp)
                    .shadow(4.dp, shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .offset(y = (-65).dp)
        ) {
            // Display loading spinner while data is being fetched
            if (movieDetails == null) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .offset(y = 200.dp)
                ) {
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
                            .offset(75.dp, (-85).dp)
                            .width(200.dp),
                        style = MaterialTheme.typography.titleLarge
                    )

                    // Save Button
                    IconButton(
                        onClick = { viewModel.addMovie(movie) },
                        modifier = Modifier
                            .align(Alignment.Center)
                            .offset(x = 40.dp, y = (-10).dp),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Save to Watchlist",
                            tint = Color.White
                        )
                    }

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
                                .clickable { activeTab = ActiveTab.DESCRIPTION },
                            color = if (activeTab == ActiveTab.DESCRIPTION) Color.White else Color.DarkGray
                        )

                        Text(
                            text = "Reviews",
                            modifier = Modifier
                                .offset(x = 20.dp)
                                .clickable { activeTab = ActiveTab.REVIEWS },
                            color = if (activeTab == ActiveTab.REVIEWS) Color.White else Color.DarkGray
                        )

                        Text(
                            text = "Cast",
                            modifier = Modifier
                                .offset(x = 80.dp)
                                .clickable { activeTab = ActiveTab.CAST },
                            color = if (activeTab == ActiveTab.CAST) Color.White else Color.DarkGray
                        )
                    }
                    // Text box for description, etc
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .align(Alignment.BottomCenter)
                            .offset(y = (-10).dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width((LocalConfiguration.current.screenWidthDp * 0.9).dp)
                                .height(200.dp) // Adjust size as needed
                                .background(Color.White)
                                .padding(16.dp) // Padding for the content inside
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Column(
                                modifier = Modifier.verticalScroll(rememberScrollState()) // Enable vertical scroll
                            ) {
                                when (activeTab) {
                                    ActiveTab.DESCRIPTION -> {
                                        Text(
                                            text = description,
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                    }

                                    ActiveTab.CAST -> {
                                        Column {
                                            cast.forEach {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(16.dp)
                                                        .background(color = Color.LightGray)
                                                ) {
                                                    Column(
                                                        modifier = Modifier
                                                            .align(Alignment.CenterStart)
                                                    ) {
                                                        Image(
                                                            painter = rememberAsyncImagePainter(
                                                                model = ImageRequest.Builder(
                                                                    LocalContext.current
                                                                )
                                                                    .data(it.profileImageUrl)
                                                                    .crossfade(true) // Enable crossfade for a smooth transition
                                                                    .build()
                                                            ),
                                                            contentDescription = "Picture of ${it.actorName}",
                                                            modifier = Modifier
                                                                .size(80.dp),
                                                            contentScale = ContentScale.Fit // or ContentScale.Fit based on your needs
                                                        )
                                                    }

                                                    Column(
                                                        modifier = Modifier
                                                            .width(190.dp)
                                                            .align(Alignment.Center)
                                                            .offset(x = 40.dp)
                                                    ) {
                                                        Text(
                                                            modifier = Modifier.align(Alignment.Start),
                                                            text = it.actorName,
                                                            style = MaterialTheme.typography.bodyMedium,
                                                            color = Color.Black
                                                        )
                                                        Text(
                                                            modifier = Modifier.align(Alignment.Start),
                                                            text = it.character,
                                                            style = MaterialTheme.typography.bodyMedium,
                                                            color = Color.Black
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    ActiveTab.REVIEWS -> {
                                        if (reviews.isNotEmpty()) {
                                            Column {
                                                reviews.forEach {
                                                    Box(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(16.dp)
                                                            .background(color = Color.LightGray)
                                                    ) {
                                                        Column(
                                                            modifier = Modifier
                                                                .padding(16.dp)
                                                                .align(Alignment.CenterStart)
                                                        ) {
                                                            Text(
                                                                modifier = Modifier.align(Alignment.Start),
                                                                text = it.author,
                                                                style = MaterialTheme.typography.bodyMedium,
                                                                color = Color.Black
                                                            )

                                                            Spacer(modifier = Modifier.height(10.dp))

                                                            Text(
                                                                modifier = Modifier.align(Alignment.Start),
                                                                text = it.content,
                                                                style = MaterialTheme.typography.bodyMedium,
                                                                color = Color.Black
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            Text(
                                                text = "There are no reviews for this movie",
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        }
                                    }
                                }
                            }
                        }
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
            NavBar(navController, "MovieDetails")
        }
    }
}
