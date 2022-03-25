package com.ttw.pagingsample.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
     primaryKeys = ["id"]
)
data class Movie(
    @field:SerializedName("id")
    val id: String,
    @field:SerializedName("title")
    val title: String,
    @field:SerializedName("poster_path")
    val poster_path: String?,
    @field:SerializedName("vote_average")
    val vote_average: Float,
    @field:SerializedName("overview")
    val overview: String,
    @field:SerializedName("release_date")
    val release_date: String,
    @field:SerializedName("popularity")
    val popularity: Float,
    @field:SerializedName("vote_count")
    val vote_count: Int,
    @field:SerializedName("video")
    val video: Boolean,/*
    @field:SerializedName("backdrop_path")
    val backdrop_path: String,*/
    @field:SerializedName("original_language")
    val original_language: String,
    @field:SerializedName("original_title")
    val original_title: String,
    @field:SerializedName("adult")
    val adult: Boolean
)