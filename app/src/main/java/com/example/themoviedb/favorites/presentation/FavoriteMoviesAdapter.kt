package com.example.themoviedb.favorites.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.themoviedb.common.domain.entities.Movie
import com.example.themoviedb.common.utils.Constants
import com.example.themoviedb.databinding.FavoriteMovieItemBinding
import kotlin.reflect.KFunction1


class FavoriteMoviesAdapter(
    private val movies: List<Movie>,
    private val onMovieClick: KFunction1<Movie, Unit>,
    private val updateMovie: (Movie) -> Unit
) : RecyclerView.Adapter<FavoriteMoviesAdapter.FavoriteMovieViewHolder>() {

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
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size
}