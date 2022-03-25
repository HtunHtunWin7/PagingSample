package com.ttw.pagingsample.repository

import android.util.Log
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
        return movieApi.getNowPlaying(page,10)
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getRemoteMovies(): Flow<PagingData<Movie>> {
        val pagingSourceFactory = { database.movieDao().getMovies() }

        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = MovieRemoteMediator(database,movieApi),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }
}