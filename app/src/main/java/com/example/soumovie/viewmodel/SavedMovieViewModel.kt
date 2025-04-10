package com.example.soumovie.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soumovie.data.AppContainer
import com.example.soumovie.data.MovieDetails
import com.example.soumovie.data.SavedMovie
import com.example.soumovie.data.SavedMovieDAO
import com.example.soumovie.model.Movie
import com.example.soumovie.repository.SavedMovieRepository
import kotlinx.coroutines.launch

class SavedMoviesViewModel(
    private val repository: SavedMovieRepository
) : ViewModel() {

    // LiveData to observe list of saved movies
    private val allSavedMovies: LiveData<List<SavedMovie>> = repository.getWatchlist()

    private fun MovieDetails.toSavedMovie(): SavedMovie {
        return SavedMovie(
            id = 0,
            movieId = this.id,                     // Save the same `id` as `movieId` (or you can customize)
            name = this.title,                     // Map `title` from Movie to `name` in SavedMovie
            rating = this.voteAverage,             // Map `vote_average` from Movie to `rating` in SavedMovie
            posterPath = this.posterPath ?: ""     // Use `posterPath`, default to "" if null
        )
    }

    // Function to insert a movie
    fun addMovie(movie: MovieDetails) {
        viewModelScope.launch {
            repository.saveMovie(movie.toSavedMovie())
        }
    }

    // Function to delete a movie
    fun deleteMovie(movie: SavedMovie) {
        viewModelScope.launch {
            repository.deleteMovie(movie)
        }
    }

    // Return watchlist
    fun getWatchlist(): LiveData<List<SavedMovie>> {
        return allSavedMovies
    }

}
