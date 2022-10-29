package com.example.themoviedb.moviedetail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.common.domain.model.Movie
import com.example.themoviedb.common.utils.DispatcherProvider
import com.example.themoviedb.home.domain.FavoriteMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val favoriteMoviesUseCase: FavoriteMoviesUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<MovieUIState> = MutableStateFlow(MovieUIState.Loading)
    val state: StateFlow<MovieUIState> = _state

    private fun addToFavorites(movie:Movie) {
        viewModelScope.launch(dispatcherProvider.IO) {
            favoriteMoviesUseCase(movie)
        }
    }

    fun handleEvent(event: MovieDetailsEvent) {
        when (event) {
            is MovieDetailsEvent.AddToFavorites -> {
                addToFavorites(event.movie)
            }
            is MovieDetailsEvent.LoadMovie -> {
                _state.value = MovieUIState.MovieDetails(event.movie)
            }
        }
    }
}