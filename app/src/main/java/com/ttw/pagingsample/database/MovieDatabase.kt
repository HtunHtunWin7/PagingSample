package com.ttw.pagingsample.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ttw.pagingsample.database.dao.KeyDao
import com.ttw.pagingsample.database.dao.MovieDao
import com.ttw.pagingsample.model.Keys
import com.ttw.pagingsample.model.Movie

@Database(
    entities = [Movie::class, Keys::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    companion object {
        operator fun invoke(context: Context): MovieDatabase {
            val databaseBuilder =
                Room.databaseBuilder(context, MovieDatabase::class.java, "movie.db")
            return databaseBuilder.build()
        }
    }

    abstract fun movieDao(): MovieDao
    abstract fun keyDao(): KeyDao
}

