/*
package com.ttw.pagingsample.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ttw.pagingsample.database.dao.MovieDao
import com.ttw.pagingsample.model.Movie

@Database(
    entities = [Movie::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    companion object {
        fun create(context: Context): MovieDatabase {
            val databaseBuilder =
                Room.databaseBuilder(context, MovieDatabase::class.java, "movie.db")
            return databaseBuilder.build()
        }
    }

    abstract fun movieDao(): MovieDao
}
*/
