/*
package com.ttw.pagingsample.database.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ttw.pagingsample.model.Movie

@Dao
abstract class MovieDao {
    @Query("SELECT * FROM movie")
    abstract fun getMovies(): PagingSource<Int, Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMovies(movie: List<Movie>)
}*/
