package com.example.soumovie.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.soumovie.data.*
import com.example.soumovie.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val repository = MovieRepository() // No need to pass ApiService here

    private val _popularMovies = MutableLiveData<List<Result>>()
    val popularMovies: LiveData<List<Result>> = _popularMovies

    private val _allMovies = MutableLiveData<List<Result>>()
    val allMovies: LiveData<List<Result>> = _allMovies // LiveData for all movies

    private val _movieDetails = MutableLiveData<MovieDetails>()
    val movieDetails: LiveData<MovieDetails> = _movieDetails

    // Function to load popular movies
    fun loadPopularMovies() {
        viewModelScope.launch {
            try {
                val response = repository.getPopularMovies()
                _popularMovies.value = response.results // response.results is a List<Result>
//                Log.e("Results", response.results.toString()) FOR DEBUGGING
            } catch (e: Exception) {
                // Handle error
//                Log.e("API_ERROR", "Exception: ${e.message}") FOR DEBUGGING
            }
        }
    }

    // Function to load all movies
    fun loadAllMovies() {
        viewModelScope.launch {
            try {
                val response = repository.getAllMovies() // Assuming this method exists
                _allMovies.value = response.results // response.results is a List<Result>
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    // Function to load movie details
    fun loadMovieDetails(id: Int) {
        viewModelScope.launch {
            try {
                val details = repository.getMovieDetails(id)
                _movieDetails.value = details
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
