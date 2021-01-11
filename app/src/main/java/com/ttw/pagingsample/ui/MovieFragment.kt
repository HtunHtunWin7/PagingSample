package com.ttw.pagingsample.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ttw.pagingsample.R
import com.ttw.pagingsample.adapters.MovieAdapter
import com.ttw.pagingsample.databinding.MovieFragmentBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class MovieFragment : Fragment(), KodeinAware {
    override val kodein by kodein()

    companion object {
        fun newInstance() = MovieFragment()

    }

    private lateinit var viewModel: MovieViewModel
    private lateinit var binding: MovieFragmentBinding
    private var searchJob: Job? = null
    private lateinit var movieAdapter: MovieAdapter
    private val factory: MovieViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.movie_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(MovieViewModel::class.java)

        setUpViews()
        getPopularMovies()
        binding.swipeRefreshLayout.setOnClickListener {
            movieAdapter.refresh()
        }
    }

    private fun setUpViews() {
        movieAdapter = MovieAdapter()
        binding.recyclerMovie.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = movieAdapter
        }
    }

    private fun getPopularMovies() {
         searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.getNowPlayingMovies()
                .collectLatest {
                    movieAdapter.submitData(it)
                }
        }
    }
}