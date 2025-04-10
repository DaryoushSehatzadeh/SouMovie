package com.example.soumovie.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedMovieDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: SavedMovie)

    @Delete
    suspend fun delete(item: SavedMovie)

    @Query("SELECT * from watchlist ORDER BY id ASC")
    fun getWatchlist(): Flow<List<SavedMovie>>
}