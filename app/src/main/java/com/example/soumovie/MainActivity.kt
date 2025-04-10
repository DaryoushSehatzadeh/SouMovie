package com.example.soumovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.soumovie.data.AppContainer
import com.example.soumovie.data.SavedMovie
import com.example.soumovie.data.Watchlist
import com.example.soumovie.model.Movie
import com.example.soumovie.pages.MovieDetails
import com.example.soumovie.pages.TermsAndConditions
import com.example.soumovie.pages.Movies
import com.example.soumovie.pages.SearchMovies
import com.example.soumovie.pages.SplashScreen
import com.example.soumovie.pages.SavedMovies
import com.example.soumovie.ui.theme.SouMovieTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = AppContainer(application)

        enableEdgeToEdge()
        setContent {
            SouMovieTheme {

                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "splash") {
                    composable("splash") {
                        SplashScreen(navController)
                    }
                    composable("tnc") {

                        TermsAndConditions(navController)
                    }
                    composable("Movies") {

                        Movies(navController, appContainer.savedMovieRepository)
                    }
                    composable("movieDetails/{movieId}") { backStackEntry ->
                        val movieId = backStackEntry.arguments?.getString("movieId")?.toInt() ?: 0
                        MovieDetails(movieId = movieId, navController, appContainer.savedMovieRepository)  // Pass the movieId to the MovieDetails screen
                    }
                    composable("SearchMovies") {

                        SearchMovies(navController, appContainer.savedMovieRepository)
                    }
                    composable("SavedMovies") {

                        SavedMovies(navController, appContainer.savedMovieRepository)
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SouMovieTheme {
        // You can define a preview of your UI here
    }
}
