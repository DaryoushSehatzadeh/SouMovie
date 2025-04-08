package com.example.soumovie.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.soumovie.data.*
import com.example.soumovie.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val repository = MovieRepository() // No need to pass ApiService here

    private val _popularMovies = MutableLiveData<List<Result>?>()
    val popularMovies: LiveData<List<Result>?> = _popularMovies

    private val _allMovies = MutableLiveData<List<Result>?>()
    val allMovies: LiveData<List<Result>?> = _allMovies

    private val _movieDetails = MutableLiveData<MovieDetails?>()
    val movieDetails: MutableLiveData<MovieDetails?> = _movieDetails

    private val _castDetails = MutableLiveData<CastDetails?>()
    val castDetails: MutableLiveData<CastDetails?> = _castDetails

    private val _reviewDetails = MutableLiveData<Reviews?>()
    val reviewDetails: MutableLiveData<Reviews?> = _reviewDetails

    fun loadPopularMovies() {
        // Call your API and update the LiveData
        // Assume fetchPopularMovies() is a suspend function to fetch data
        viewModelScope.launch {
            try {
                _popularMovies.value = repository.getPopularMovies()
            } catch (e: Exception) {
                // Handle exception
                _popularMovies.value = null
            }
        }
    }

    fun loadAllMovies() {
        // Call your API and update the LiveData
        viewModelScope.launch {
            try {
                _allMovies.value = repository.getAllMovies()
            } catch (e: Exception) {
                // Handle exception
                _allMovies.value = null
            }
        }
    }

    // Function to load movie details
    fun loadMovieDetails(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getMovieDetails(id) // API call
                _movieDetails.postValue(response)
            } catch (e: Exception) {
                _movieDetails.value = null
            }
        }
    }

    // Get cast
    fun loadCastDetails(id: Int) {
        viewModelScope.launch {
            try {
                _castDetails.value = repository.getCast(id) // API call
            } catch (e: Exception) {
                _castDetails.value = null
            }
        }
    }

    // Get reviews
    fun loadReviewDetails(id: Int) {
        viewModelScope.launch {
            try {
                _reviewDetails.value = repository.getReviews(id) // API call
            } catch (e: Exception) {
                _reviewDetails.value = null
            }
        }
    }
}
