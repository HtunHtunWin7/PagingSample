package com.ttw.pagingsample.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ttw.pagingsample.database.dao.MovieDao
import com.ttw.pagingsample.database.dao.RemoteKeysDao
import com.ttw.pagingsample.model.Movie
import com.ttw.pagingsample.model.RemoteKeys

@Database(
    entities = [Movie::class, RemoteKeys::class],
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
    abstract fun remoteKeysDao(): RemoteKeysDao
}

