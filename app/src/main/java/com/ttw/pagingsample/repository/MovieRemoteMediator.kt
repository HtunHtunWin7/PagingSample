package com.ttw.pagingsample.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ttw.pagingsample.api.MovieApi
import com.ttw.pagingsample.database.MovieDatabase
import com.ttw.pagingsample.model.Keys
import com.ttw.pagingsample.model.Movie
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException
import java.security.Key

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val initialPage: Int,
    private val service: MovieApi,
    private val database: MovieDatabase
) :
    RemoteMediator<Int, Movie>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: initialPage
                }
                LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteForLastItem(state)
                        ?: throw InvalidObjectException("Result is empty")
                    remoteKeys.nextKey ?: return MediatorResult.Success(true)
                }
            }
            val response = service.getNowPlaying(page = page).body()
            val endOfPaginationReached = response?.movieResults!!.size < state.config.pageSize
            if (response != null) {

                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        database.keyDao().clearKeys()
                        database.movieDao().clearMovie()
                    }
                    val preKey = if (page == initialPage) null else page - 1
                    val nextKey = if (endOfPaginationReached) null else page + 1
                    val keys = response.movieResults.map {
                        Keys(newsId = it.id, prevKey = preKey, nextKey = nextKey)
                    }
                    database.movieDao().insertMovies(movie = response.movieResults)
                    database.keyDao().saveKeys(key = keys)
                }
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteForLastItem(state: PagingState<Int, Movie>): Keys? {
        return state.lastItemOrNull()?.let { key ->
            database.withTransaction { database.keyDao().getKeys(key.id) }
        }
    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Movie>): Keys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.withTransaction { database.keyDao().getKeys(id) }
            }
        }

    }
}
