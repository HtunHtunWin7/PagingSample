package com.ttw.pagingsample.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ttw.pagingsample.model.Movie


@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getMovies(): PagingSource<Int, Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movie: List<Movie>)

    @Query("DELETE FROM movie")
    suspend fun clearMovie()
}
