package com.example.themoviedb.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applications.domain.entities.Movie
import com.applications.utils.DispatcherProvider
import com.example.themoviedb.favorites.domain.GetFavoriteMovies
import com.applications.domain.usecases.FavoriteMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    getFavoriteMovies: GetFavoriteMovies,
    private val favoriteMoviesUseCase: FavoriteMoviesUseCase
) : ViewModel() {

    val favoriteMovies = getFavoriteMovies()

    private fun addToFavorites(movie: Movie) {
        viewModelScope.launch(dispatcher.IO) {
            favoriteMoviesUseCase(movie)
        }
    }

    fun handleEvents(event: FavoritesMovieEvent){
        when(event){
            is FavoritesMovieEvent.AddToFavorites -> {
                addToFavorites(event.movie)
            }
        }
    }
}