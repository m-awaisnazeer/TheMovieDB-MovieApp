package com.example.awaisahmadassignment.common.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.awaisahmadassignment.R
import com.example.awaisahmadassignment.common.domain.model.Movie
import com.example.awaisahmadassignment.common.utils.Constants.MOVIE_PATH
import com.example.awaisahmadassignment.databinding.MovieItemBinding

class MovieAdapter(private val updateMovie: (Int, Boolean) -> Unit) :
    PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(COMPARATOR) {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var binding: MovieItemBinding

        constructor(binding: MovieItemBinding) : this(binding.root) {
            this.binding = binding
        }

        fun bind(item: Movie) {
            Glide.with(binding.root.context).load(MOVIE_PATH.plus(item.posterPath))
                .into(binding.imgPoster)
            binding.txtRelease.text = item.releaseDate
            binding.txtMovieName.text = item.title
            binding.imgFavorite.setOnClickListener {
                updateMovie(item.id, !item.isFavorite)
            }

            if (item.isFavorite){
                binding.imgFavorite.setBackgroundResource(R.drawable.ic_favorite_24)
            }else{
                binding.imgFavorite.setBackgroundResource(R.drawable.ic_favorite_border_24)
            }

            binding.root.setOnClickListener {
                val movieDetail = Bundle()
                movieDetail.putInt("movieId",item.id)
                it.findNavController().navigate(R.id.action_navigation_home_to_movieDetailFragment,movieDetail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    companion object {
        private val COMPARATOR = object :
            DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
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