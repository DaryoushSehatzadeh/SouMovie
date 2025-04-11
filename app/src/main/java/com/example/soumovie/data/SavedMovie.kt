package com.example.soumovie.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "Watchlist",
    indices = [Index(value = ["movieId"], unique = true)]
)
data class SavedMovie(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val movieId: Int,
    val name: String,
    val rating: Double,
    val posterPath: String
)