package com.example.themoviedb.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.applications.domain.entities.Movie
import com.applications.utils.DispatcherProvider
import com.applications.utils.Resource
import com.applications.domain.usecases.FavoriteMoviesUseCase
import com.example.themoviedb.search.domain.SearchMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val searchMovies: SearchMovies,
    private val favoriteMoviesUseCase: FavoriteMoviesUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<SearchMovieUiState> =
        MutableStateFlow(SearchMovieUiState.Initial)
    val state: StateFlow<SearchMovieUiState> = _state

    private var job = Job()
        get() {
            if (field.isCancelled) field = Job()
            return field
        }

    private fun updateQuery(newText: String) {
        job.cancel()
        _state.value = SearchMovieUiState.Loading
        if (newText.isEmpty()){
            _state.value = SearchMovieUiState.Initial
            return
        }
        val coroutineContext = job + dispatcherProvider.IO
        viewModelScope.launch(coroutineContext) {
            searchMovies(newText).collect {
                when (it) {
                    is Resource.Error -> {
                        _state.value = SearchMovieUiState.Error(it.message ?: "Some Error Occurred")
                    }
                    is Resource.Success -> {
                        if (it.data.isNullOrEmpty()) {
                            _state.value = SearchMovieUiState.NoResultFound
                        } else {
                            _state.value = SearchMovieUiState.Success(it.data as  List<Movie>)
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