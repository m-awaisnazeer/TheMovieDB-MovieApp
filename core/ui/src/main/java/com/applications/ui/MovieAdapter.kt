package com.applications.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.applications.domain.domain.entities.Movie
import com.applications.ui.databinding.MovieItemBinding
import com.applications.utils.Constants.MOVIE_PATH
import kotlin.reflect.KFunction1

class MovieAdapter(
    private val updateMovie: KFunction1<Movie, Unit>,
    private val onMovieClick: KFunction1<Movie, Unit>
) :
    PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(COMPARATOR) {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var binding: MovieItemBinding

        constructor(binding: MovieItemBinding) : this(binding.root) {
            this.binding = binding
        }

        fun bind(movie: Movie) {
            Glide.with(binding.root.context).load(MOVIE_PATH.plus(movie.posterPath))
                .into(binding.imgPoster)
            binding.txtRelease.text = movie.releaseDate
            binding.txtMovieName.text = movie.title
            binding.imgFavorite.setOnClickListener {
                updateMovie(movie.copy(isFavorite = !movie.isFavorite))
            }
            binding.imgFavorite.isChecked = movie.isFavorite

            binding.root.setOnClickListener {
                onMovieClick(movie)
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