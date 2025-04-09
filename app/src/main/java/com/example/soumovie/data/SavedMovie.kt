package com.example.soumovie.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist")
data class SavedMovie(
    @PrimaryKey
    val id: Int,
    val name: String,
    val rating: Double,
    val posterPath: String
)