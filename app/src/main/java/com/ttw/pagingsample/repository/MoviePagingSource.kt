package com.ttw.pagingsample.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingSource
import com.ttw.pagingsample.api.MovieApi
import com.ttw.pagingsample.model.Movie
import retrofit2.HttpException
import java.io.IOException

private const val MOVIE_STARTING_PAGE_INDEX = 1

class MoviePagingSource(private val repository: MovieRepository) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val nextPage = params.key ?: 1
        return try {
            val response = repository.getPopularMovies(nextPage).body()
            val movie = response!!.movieResults

            Log.d("Page Size >>>>>", response.page.toString())

            LoadResult.Page(
                data = movie,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (response.movieResults.isEmpty())
                    null else nextPage + 1
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}