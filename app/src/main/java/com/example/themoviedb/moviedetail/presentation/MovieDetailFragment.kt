package com.example.themoviedb.moviedetail.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.themoviedb.common.domain.entities.Movie
import com.example.themoviedb.common.utils.Constants
import com.example.themoviedb.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieDetailViewModel by viewModels()

    private val args: MovieDetailFragmentArgs by navArgs()
    private val currentMovie: Movie by lazy {
        args.movie
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        viewModel.handleEvent(MovieDetailsEvent.LoadMovie(currentMovie))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToUpdates()
        binding.btnFavorite.setOnClickListener {
            viewModel.handleEvent(
                MovieDetailsEvent.AddToFavorites(
                    currentMovie.copy(
                        isFavorite = !currentMovie.isFavorite
                    )
                )
            )
        }
    }

    private fun subscribeToUpdates() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        is MovieUIState.Loading -> {
                            binding.movieProgressBar.isVisible = true
                        }
                        is MovieUIState.MovieDetails -> {
                            displayMovieDetails(it.movie)
                        }
                    }
                }
            }
        }
    }

    private fun displayMovieDetails(movie: Movie) {
        binding.movieProgressBar.isVisible = false
        binding.txtMovieName.text = movie.title
        binding.txtMovieRelease.text = movie.releaseDate
        binding.txtMovieOverView.text = movie.overView
        binding.btnFavorite.isChecked = movie.isFavorite
        Glide.with(binding.root.context)
            .load(Constants.MOVIE_PATH.plus(movie.posterPath))
            .into(binding.imgMovieBanner)
    }
}