package com.example.soumovie.data

import android.content.Context
import com.example.soumovie.repository.SavedMovieRepository

class AppContainer(context: Context) {
    val savedMovieRepository: SavedMovieRepository by lazy {
        SavedMovieRepository(Watchlist.getDatabase(context).savedMovieDAO())
    }
}

