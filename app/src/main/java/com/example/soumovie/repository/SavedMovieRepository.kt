package com.example.soumovie.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.soumovie.data.SavedMovie
import com.example.soumovie.data.SavedMovieDAO

class SavedMovieRepository(private val savedMovieDAO: SavedMovieDAO){
    suspend fun saveMovie(movie: SavedMovie) = savedMovieDAO.insert(movie)
    suspend fun deleteMovie(movie: SavedMovie) = savedMovieDAO.delete(movie)

    fun getWatchlist(): LiveData<List<SavedMovie>> = savedMovieDAO.getWatchlist().asLiveData()}