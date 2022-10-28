package com.example.themoviedb.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun addToFavorites(movieId: Int, isFavorite: Boolean) {
        viewModelScope.launch(dispatcher.IO) {
            favoriteMoviesUseCase(movieId, isFavorite)
        }
    }
}