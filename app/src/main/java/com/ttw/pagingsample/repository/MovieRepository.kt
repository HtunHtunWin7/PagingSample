package com.ttw.pagingsample.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ttw.pagingsample.api.MovieApi
import com.ttw.pagingsample.model.Movie
import kotlinx.coroutines.flow.Flow

class MovieRepository constructor(private val movieApi: MovieApi) {
    fun getNowPlayingMovies(): Flow<PagingData<Movie>> {
        return Pager(config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                MoviePagingSource(movieApi)
            }).flow

    }
}