package com.ttw.pagingsample.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingSource
import com.ttw.pagingsample.api.MovieApi
import com.ttw.pagingsample.model.Movie
import retrofit2.HttpException
import java.io.IOException

private const val MOVIE_STARTING_PAGE_INDEX = 1

class MoviePagingSource(private val api: MovieApi) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: MOVIE_STARTING_PAGE_INDEX
        return try {
            val itemPage = params.loadSize
            val response = api.getNowPlaying(position).execute()
            val movie = response.body()!!.movieResults

            LoadResult.Page(
                data = movie,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (movie.isEmpty()) null else position + 1
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}