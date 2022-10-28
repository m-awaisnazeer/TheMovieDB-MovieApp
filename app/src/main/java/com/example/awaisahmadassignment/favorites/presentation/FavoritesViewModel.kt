package com.example.awaisahmadassignment.favorites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.awaisahmadassignment.common.utils.DispatcherProvider
import com.example.awaisahmadassignment.favorites.domain.GetFavoriteMovies
import com.example.awaisahmadassignment.home.domain.FavoriteMoviesUseCase
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