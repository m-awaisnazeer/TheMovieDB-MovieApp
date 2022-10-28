package com.example.awaisahmadassignment.common.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.awaisahmadassignment.common.data.cache.model.CachedMovie
import com.example.awaisahmadassignment.common.utils.Constants.MOVIE_PATH
import com.example.awaisahmadassignment.databinding.MovieItemBinding

class MovieAdapter: PagingDataAdapter<CachedMovie, MovieAdapter.MovieViewHolder>(COMPARATOR) {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var binding: MovieItemBinding

        constructor(binding: MovieItemBinding) : this(binding.root) {
            this.binding = binding
        }

        fun bind(item: CachedMovie){
            binding.imgFavorite
            Glide.with(binding.root.context)
                .load(MOVIE_PATH.plus(item.posterPath))
                .into(binding.imgPoster)
            binding.txtRelease.text = item.releaseDate
            binding.txtMovieName.text = item.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    companion object{
        private val COMPARATOR = object :
            DiffUtil.ItemCallback<CachedMovie>() {
            override fun areItemsTheSame(oldItem: CachedMovie, newItem: CachedMovie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CachedMovie, newItem: CachedMovie): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }
}