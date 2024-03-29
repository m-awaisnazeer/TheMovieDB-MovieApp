package com.applications.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applications.domain.entities.Movie
import com.applications.domain.usecases.FavoriteMoviesUseCase
import com.applications.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val favoriteMoviesUseCase: FavoriteMoviesUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<MovieUIState> = MutableStateFlow(MovieUIState.Loading)
    val state: StateFlow<MovieUIState> = _state

    private fun addToFavorites(movie: Movie) {
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