package com.example.soumovie.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SavedMovie::class], version = 5, exportSchema = false)
abstract class Watchlist : RoomDatabase() {
    abstract fun savedMovieDAO(): SavedMovieDAO
    companion object {
        @Volatile
        private var Instance: Watchlist? = null

        fun getDatabase(context: Context): Watchlist {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, Watchlist::class.java, "item_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }

}