package com.ttw.pagingsample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ttw.pagingsample.model.Movie
import com.ttw.pagingsample.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MovieViewModel(private val repository: MovieRepository) :
    ViewModel() {
    private lateinit var currentResult: Flow<PagingData<Movie>>

    fun getNowPlayingMovies(): Flow<PagingData<Movie>> {
        val newResult: Flow<PagingData<Movie>> =
            repository.getNowPlayingMovies().cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }

}