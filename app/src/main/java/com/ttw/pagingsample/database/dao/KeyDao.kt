package com.ttw.pagingsample.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ttw.pagingsample.model.Keys


@Dao
interface KeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveKeys(key: List<Keys>)

    @Query("SELECT * FROM keys WHERE newsId=:id")
    suspend fun getKeys(id: Long): Keys

    @Query("DELETE FROM keys")
    suspend fun clearKeys()

}