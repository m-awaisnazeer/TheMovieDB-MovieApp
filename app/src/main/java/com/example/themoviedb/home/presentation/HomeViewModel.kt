package com.example.themoviedb.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.applications.domain.domain.entities.Movie
import com.applications.utils.DispatcherProvider
import com.example.themoviedb.home.domain.FavoriteMoviesUseCase
import com.example.themoviedb.home.domain.GetAllMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    getAllMovies: GetAllMovies,
    val favoriteMovies: FavoriteMoviesUseCase
) :
    ViewModel() {
    val list: Flow<PagingData<Movie>> = getAllMovies()

    private fun addToFavorites(movie: Movie) {
        viewModelScope.launch(dispatcher.IO) {
            favoriteMovies(movie)
        }
    }

    fun handleEvents(event: HomeMoviesEvent){
        when(event){
            is HomeMoviesEvent.AddToFavorites -> {
                addToFavorites(event.movie)
            }
        }
    }
}