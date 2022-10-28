package com.example.awaisahmadassignment.moviedetail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.awaisahmadassignment.common.utils.DispatcherProvider
import com.example.awaisahmadassignment.moviedetail.domain.GetMovie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val dispatcherProvider: DispatcherProvider,
    private val getMovie: GetMovie
) : ViewModel() {

    private val _state: MutableStateFlow<MovieUIState> = MutableStateFlow(MovieUIState.Loading)
    val state: StateFlow<MovieUIState> = _state

    fun getMovieById(movieId: Int) {
        viewModelScope.launch(dispatcherProvider.IO) {
            getMovie(movieId).collect {
                _state.value = MovieUIState.Success(it)
            }
        }
    }
}