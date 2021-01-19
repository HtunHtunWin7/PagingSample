package com.ttw.pagingsample.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ttw.pagingsample.api.MovieApi
import com.ttw.pagingsample.api.MovieResponse
import com.ttw.pagingsample.database.MovieDatabase
import com.ttw.pagingsample.model.Movie
import retrofit2.Response
import kotlinx.coroutines.flow.Flow

class MovieRepository constructor(private val movieApi: MovieApi,private val database: MovieDatabase) {

    suspend fun getPopularMovies(page: Int) : Response<MovieResponse> {
        return movieApi.getNowPlaying(page)
    }
    @OptIn(ExperimentalPagingApi::class)
    fun getMovies():Flow<PagingData<Movie>> {
        return Pager(
            PagingConfig(pageSize = 10, enablePlaceholders = false, prefetchDistance = 3),
            remoteMediator = MovieRemoteMediator(1, database = database,service = movieApi ),
            pagingSourceFactory = { database.movieDao().getMovies() }
        ).flow
    }/*
    fun getNowPlayingMovies(): Flow<PagingData<Movie>> {
        return Pager(config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                MoviePagingSource(movieApi)
            }).flow

    }*/
}