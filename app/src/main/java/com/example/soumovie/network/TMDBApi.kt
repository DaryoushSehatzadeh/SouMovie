package com.example.soumovie.network

import com.example.soumovie.data.AllMovies
import com.example.soumovie.data.MovieDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface TMDBApi {

    // Existing method to get popular movies
    @GET("movie/popular")
    suspend fun getPopularMovies(): AllMovies

    // New method to get all movies
    @GET("discover/movie")
    suspend fun getAllMovies(): AllMovies // Adjust the endpoint as needed

    // Existing method to get movie details
    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") id: Int): MovieDetails
}
