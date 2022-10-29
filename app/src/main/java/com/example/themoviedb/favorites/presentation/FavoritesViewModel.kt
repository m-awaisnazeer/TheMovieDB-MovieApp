package com.example.themoviedb.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.common.domain.model.Movie
import com.example.themoviedb.common.utils.DispatcherProvider
import com.example.themoviedb.favorites.domain.GetFavoriteMovies
import com.example.themoviedb.home.domain.FavoriteMoviesUseCase
import kotlinx.coroutines.launch

class FavoritesViewModel(
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