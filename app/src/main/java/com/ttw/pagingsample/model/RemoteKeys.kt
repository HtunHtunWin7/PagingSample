package com.ttw.pagingsample.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * if repoId is long, pager call single page
 * @@@ always call page 2 error
 * @@@@ solve with string
 */

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val repoId: String,
    val prevKey: Int?,
    val nextKey: Int?
)
