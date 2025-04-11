package com.example.soumovie.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soumovie.data.AppContainer
import com.example.soumovie.data.MovieDetails
import com.example.soumovie.data.Result
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
            movieId = this.id,
            name = this.title,
            rating = this.voteAverage,
            posterPath = this.posterPath
        )
    }

    private fun Result.toSavedMovie(): SavedMovie {
        return SavedMovie(
            id = 0,
            movieId = this.id,
            name = this.title,
            rating = this.voteAverage,
            posterPath = this.posterPath
        )
    }

    // Function to insert a movie
    fun addMovie(movie: MovieDetails) {
        viewModelScope.launch {
            repository.saveMovie(movie.toSavedMovie())
        }
    }

    // Function to insert a movie
    fun addMovie(movie: Result) {
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

    // Function to delete a movie
    fun deleteMovie(movie: MovieDetails) {
        viewModelScope.launch {
            repository.deleteMovie(movie.toSavedMovie())
        }
    }

    // Function to delete a movie
    fun deleteMovie(movie: Result) {
        viewModelScope.launch {
            repository.deleteMovie(movie.toSavedMovie())
        }
    }

    // Return watchlist
    fun getWatchlist(): LiveData<List<SavedMovie>> {
        return allSavedMovies
    }

}
