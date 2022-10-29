package com.example.themoviedb.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.common.domain.model.Movie
import com.example.themoviedb.common.utils.DispatcherProvider
import com.example.themoviedb.common.utils.Resource
import com.example.themoviedb.home.domain.FavoriteMoviesUseCase
import com.example.themoviedb.search.domain.SearchMovies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val searchMovies: SearchMovies,
    private val favoriteMoviesUseCase: FavoriteMoviesUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<SearchMovieUiState> =
        MutableStateFlow(SearchMovieUiState.Initial)
    val state: StateFlow<SearchMovieUiState> = _state

    private fun updateQuery(newText: String) {
        _state.value = SearchMovieUiState.Loading
        if (newText.isEmpty()){
            _state.value = SearchMovieUiState.Initial
            return
        }
        viewModelScope.launch(dispatcherProvider.IO) {
            searchMovies(newText).collect {
                when (it) {
                    is Resource.Error -> {
                        _state.value = SearchMovieUiState.Error(it.message ?: "Some Error Occurred")
                    }
                    is Resource.Success -> {
                        if (it.data.isNullOrEmpty()) {
                            _state.value = SearchMovieUiState.NoResultFound
                        } else {
                            _state.value = SearchMovieUiState.Success(it.data)
                        }
                    }
                }
            }
        }
    }

    fun handleEvents(event: SearchMovieEvent) {
        when (event) {
            SearchMovieEvent.OnTextCleared -> {
                _state.value = SearchMovieUiState.Initial
            }
            is SearchMovieEvent.QueryInput -> {
                updateQuery(event.input)
            }
            is SearchMovieEvent.AddToFavorites -> {
                addToFavorites(event.movie)
            }
        }
    }

    fun addToFavorites(movie: Movie) {
        viewModelScope.launch(dispatcherProvider.IO) {
            favoriteMoviesUseCase(movie)
        }
    }
}