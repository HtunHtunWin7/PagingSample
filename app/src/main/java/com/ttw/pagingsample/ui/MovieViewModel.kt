package com.ttw.pagingsample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ttw.pagingsample.model.Movie
import com.ttw.pagingsample.repository.MoviePagingSource
import com.ttw.pagingsample.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MovieViewModel(private val pagingSource: MoviePagingSource,private val repository: MovieRepository) :
    ViewModel() {
    private lateinit var currentResult: Flow<PagingData<Movie>>

    val moviePager = Pager(
        config = PagingConfig(10)
    ) {
        pagingSource
    }.flow.cachedIn(viewModelScope)

    fun  getMovies():Flow<PagingData<Movie>>{
        return repository.getRemoteMovies()
    }
}