package com.ttw.pagingsample.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ttw.pagingsample.databinding.NetworkStateItemBinding


class MovieLoadingStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<MovieLoadingStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState, retry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            NetworkStateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    class LoadStateViewHolder(private val binding: NetworkStateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState, retry: () -> Unit) {
            binding.apply {

                if (loadState is LoadState.Loading) {
                    progressBarItem.visibility = View.VISIBLE;
                    errorMsgItem.visibility = View.GONE
                    retyBtn.visibility = View.GONE

                } else {
                    progressBarItem.visibility = View.GONE
                }

                if (loadState is LoadState.Error) {
                    errorMsgItem.visibility = View.VISIBLE
                    retyBtn.visibility = View.VISIBLE
                    errorMsgItem.text = loadState.error.localizedMessage
                }

                if (loadState is LoadState.NotLoading && loadState.endOfPaginationReached) {
                    errorMsgItem.visibility = View.VISIBLE
                    errorMsgItem.text = "You have reached the end"
                }

                retyBtn.setOnClickListener {
                    retry.invoke()
                }
            }
        }
    }
}