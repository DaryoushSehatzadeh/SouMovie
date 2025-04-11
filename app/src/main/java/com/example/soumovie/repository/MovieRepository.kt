package com.example.soumovie.repository

import com.example.soumovie.data.AllMovies
import com.example.soumovie.data.CastDetails
import com.example.soumovie.data.MovieDetails
import com.example.soumovie.data.Reviews
import com.example.soumovie.network.NetworkModule

class MovieRepository {

    // Use the NetworkModule's api instance
    private val apiService = NetworkModule.api

    // Fetch popular movies
    suspend fun getPopularMovies(): List<com.example.soumovie.data.Result> {
        val response = apiService.getPopularMovies()  // This returns AllMovies
        return response.results  // Extract only the List<Result> from the response
    }

    // Fetch all movies
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
        return apiService.getMovieDetails(id)
    }

    // Fetch cast by movie ID
    suspend fun getCast(id: Int): CastDetails {
        return apiService.getCast(id)
    }

    // Fetch reviews by movie ID
    suspend fun getReviews(id: Int): Reviews {
        return apiService.getReviews(id)
    }

    // Search movies
    suspend fun searchMovies(query: String): List<com.example.soumovie.data.Result>  {

        val filteredMovies = mutableListOf<com.example.soumovie.data.Result>()

        for (currentPage in 1..5) {
            val response = apiService.searchMovies(page = currentPage, query)
            filteredMovies.addAll(response.results)
        }

        return filteredMovies
    }

}
