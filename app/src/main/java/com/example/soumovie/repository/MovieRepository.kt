package com.example.soumovie.repository

import com.example.soumovie.data.AllMovies
import com.example.soumovie.data.MovieDetails
import com.example.soumovie.network.NetworkModule

class MovieRepository {

    // Use the NetworkModule's api instance
    private val apiService = NetworkModule.api

    // Fetch popular movies
    suspend fun getPopularMovies(): AllMovies {
        return apiService.getPopularMovies() // Assuming you have this method in your TMDBApi interface
    }

    // Fetch all movies (you may need to define this in your API interface)
    suspend fun getAllMovies(): AllMovies {
        return apiService.getAllMovies() // Assuming this method exists
    }

    // Fetch movie details by ID
    suspend fun getMovieDetails(id: Int): MovieDetails {
        return apiService.getMovieDetails(id) // Assuming this method exists in TMDBApi
    }
}
