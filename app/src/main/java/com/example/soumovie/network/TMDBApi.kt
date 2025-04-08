package com.example.soumovie.network

import com.example.soumovie.data.AllMovies
import com.example.soumovie.data.MovieDetails
import com.example.soumovie.data.CastDetails
import com.example.soumovie.data.Reviews
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {

    // Existing method to get popular movies
    @GET("movie/popular")
    suspend fun getPopularMovies(): AllMovies

    // New method to get all movies
    @GET("discover/movie")
    suspend fun getAllMovies(@Query("page") page: Int): AllMovies

    // Existing method to get movie details
    @GET("movie/{id}")
    suspend fun getMovieDetails(@Path("id") id: Int): MovieDetails

    // Get Cast
    @GET("movie/{id}/credits")
    suspend fun getCast(@Path("id") id: Int): CastDetails

    // Get Review
    @GET("movie/{id}/reviews")
    suspend fun getReviews(@Path("id") id: Int): Reviews
}
