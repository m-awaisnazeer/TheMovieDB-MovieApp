package com.example.themoviedb.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themoviedb.common.utils.DispatcherProvider
import com.example.themoviedb.common.utils.Resource
import com.example.themoviedb.search.domain.SearchMovies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val dispatcherProvider: DispatcherProvider, private val searchMovies: SearchMovies
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

    fun handleEvents(event: SearchEvent) {
        when(event) {
            SearchEvent.OnTextCleared -> {
                _state.value = SearchMovieUiState.Initial
            }
            is SearchEvent.QueryInput -> {
                updateQuery(event.input)
            }
        }
    }
}