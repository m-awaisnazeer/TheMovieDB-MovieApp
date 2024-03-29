package com.applications.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.applications.domain.entities.Movie
import com.applications.ui.databinding.FavoriteMovieItemBinding
import com.applications.utils.Constants
import kotlin.reflect.KFunction1


class FavoriteMoviesAdapter(
    private val onMovieClick: KFunction1<Movie, Unit>,
    private val updateMovie: (Movie) -> Unit
) : ListAdapter<Movie, FavoriteMoviesAdapter.FavoriteMovieViewHolder>(DIFF_CALLBACK) {

    inner class FavoriteMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var binding: FavoriteMovieItemBinding

        constructor(binding: FavoriteMovieItemBinding) : this(binding.root) {
            this.binding = binding
        }

        fun bind(movie: Movie) {
            binding.imgFavorite.isChecked = movie.isFavorite
            binding.txtFavoriteMovieName.text = movie.title
            binding.txtFavoriteMovieRelease.text = movie.releaseDate
            Glide.with(binding.root.context)
                .load(Constants.MOVIE_PATH.plus(movie.posterPath))
                .into(binding.imgFavoritePoster)

            binding.imgFavorite.setOnClickListener {
                updateMovie(movie.copy(isFavorite = !movie.isFavorite))
            }

            binding.root.setOnClickListener {
                onMovieClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        val binding = FavoriteMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>(){
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.equals(newItem)
            }
        }
    }
}