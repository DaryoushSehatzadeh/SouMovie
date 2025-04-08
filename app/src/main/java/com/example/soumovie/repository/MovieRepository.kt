package com.example.soumovie.repository

import com.example.soumovie.data.AllMovies
import com.example.soumovie.data.MovieDetails
import com.example.soumovie.network.NetworkModule

class MovieRepository {

    // Use the NetworkModule's api instance
    private val apiService = NetworkModule.api

    // Fetch popular movies
    suspend fun getPopularMovies(): List<com.example.soumovie.data.Result> {
        val response = apiService.getPopularMovies()  // This returns AllMovies
        return response.results  // Extract only the List<Result> from the response
    }

    // Fetch all movies (you may need to define this in your API interface)
    suspend fun getAllMovies(): List<com.example.soumovie.data.Result> {

        val allMovies = mutableListOf<com.example.soumovie.data.Result>()

        for (currentPage in 1..5) {
            val response = apiService.getAllMovies(page = currentPage)
            allMovies.addAll(response.results)
        }

        return allMovies  // Extract only the List<Result> from the response
    }

    // Fetch movie details by ID
    suspend fun getMovieDetails(id: Int): MovieDetails {
        return apiService.getMovieDetails(id) // Assuming this method exists in TMDBApi
    }
}
