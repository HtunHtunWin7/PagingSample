package com.ttw.pagingsample.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ttw.pagingsample.R
import com.ttw.pagingsample.adapters.MovieAdapter.*
import com.ttw.pagingsample.databinding.MovieItemBinding
import com.ttw.pagingsample.model.Movie

class MovieAdapter : PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {
    private val imgPath = "https://image.tmdb.org/t/p/w500/"

    inner class MovieViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            Glide.with(binding.root.context).load(imgPath + movie.poster_path)
                .into(binding.imgMovie)
            binding.txtMovieTitle.text = movie.title
            binding.txtDateTime.text = movie.release_date
            binding.ratingBar.rating = movie.popularity / 50
            binding.txtOriginalTitle.text = movie.original_title
        }

    }

    private class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}