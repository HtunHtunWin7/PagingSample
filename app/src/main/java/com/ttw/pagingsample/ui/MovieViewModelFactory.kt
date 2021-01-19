package com.ttw.pagingsample.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ttw.pagingsample.repository.MoviePagingSource
import com.ttw.pagingsample.repository.MovieRepository

@Suppress("UNCHECKED_CAST")
class MovieViewModelFactory(private val pagingSource: MoviePagingSource,private val repository: MovieRepository): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(pagingSource,repository) as T
    }
}