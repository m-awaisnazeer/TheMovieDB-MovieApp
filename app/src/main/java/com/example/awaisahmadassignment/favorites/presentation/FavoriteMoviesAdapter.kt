package com.example.awaisahmadassignment.favorites.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.awaisahmadassignment.R
import com.example.awaisahmadassignment.common.domain.model.Movie
import com.example.awaisahmadassignment.common.utils.Constants
import com.example.awaisahmadassignment.databinding.FavoriteMovieItemBinding


class FavoriteMoviesAdapter(
    private val movies: List<Movie>,
    private val updateMovie: (Int, Boolean) -> Unit
) : RecyclerView.Adapter<FavoriteMoviesAdapter.FavoriteMovieViewHolder>() {

    inner class FavoriteMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var binding: FavoriteMovieItemBinding

        constructor(binding: FavoriteMovieItemBinding) : this(binding.root) {
            this.binding = binding
        }

        fun bind(movie: Movie) {
            binding.txtFavoriteMovieName.text = movie.title
            binding.txtFavoriteMovieRelease.text = movie.releaseDate
            Glide.with(binding.root.context)
                .load(Constants.MOVIE_PATH.plus(movie.posterPath))
                .into(binding.imgFavoritePoster)

            binding.imgFavorite.setOnClickListener {
                updateMovie(movie.id,!movie.isFavorite)
            }

            binding.root.setOnClickListener {
                val movieDetail = Bundle()
                movieDetail.putInt("movieId",movie.id)
                it.findNavController().navigate(R.id.action_navigation_favorites_to_movieDetailFragment,movieDetail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        val binding =
            FavoriteMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size
}