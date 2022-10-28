package com.example.awaisahmadassignment.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.awaisahmadassignment.common.domain.model.Movie
import com.example.awaisahmadassignment.common.utils.DispatcherProvider
import com.example.awaisahmadassignment.home.domain.FavoriteMoviesUseCase
import com.example.awaisahmadassignment.home.domain.GetAllMovies
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel(
    val dispatcher: DispatcherProvider,
    getAllMovies: GetAllMovies,
    val favoriteMovies: FavoriteMoviesUseCase
) :
    ViewModel() {
    val list: Flow<PagingData<Movie>> = getAllMovies()

    fun addToFavorites(id: Int, isFavorite: Boolean) {
        viewModelScope.launch(dispatcher.IO) {
            favoriteMovies(id, isFavorite)
        }
    }
}