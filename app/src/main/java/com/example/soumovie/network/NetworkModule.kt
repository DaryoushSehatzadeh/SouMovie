package com.example.soumovie.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Singleton object that builds and provides the Retrofit API instance
object NetworkModule {

    // Base URL for TMDB API requests
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    // Bearer token (API v4 Access Token) used for all requests
    // This token is required in the Authorization header
    private const val AUTH_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkMDQ3NGVhMWIxMTlhZDRkNWZkZGY5YTNkYTYwY2VlYyIsIm5iZiI6MTc0MjE0OTMyNS4zMjQsInN1YiI6IjY3ZDcxNmNkMzE1MzhkZTYwOGYxYjc4NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.ceKlaEGDZzOt_rs90dMTIps-WsOQDQX4BEGvHFg9d5U" // Replace with your actual full token

    // Interceptor that automatically adds the Authorization and Content-Type headers to each request
    private val authInterceptor = Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", AUTH_TOKEN) // Required for TMDB API
            .addHeader("Content-Type", "application/json") // Recommended header for JSON-based APIs
            .build()
        chain.proceed(newRequest) // Continue the request with new headers
    }

    // 🔧 OkHttpClient with the authInterceptor attached
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

//     🚀 Retrofit instance configured with base URL, HTTP client, and Gson for JSON parsing
    val api: TMDBApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // TMDB API root
            .client(httpClient) // Attach client with headers
            .addConverterFactory(GsonConverterFactory.create()) // Parse JSON into Kotlin data classes
            .build()
            .create(TMDBApi::class.java) // Create the TMDB API interface implementation
    }
}

