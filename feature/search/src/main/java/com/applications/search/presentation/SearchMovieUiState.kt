package com.applications.search.presentation

import com.applications.domain.entities.Movie

sealed class SearchMovieUiState {
    data class Error(val message: String) : SearchMovieUiState()
    object Loading : SearchMovieUiState()
    object Initial : SearchMovieUiState()
    data class Success(val data: List<Movie>) : SearchMovieUiState()
    object NoResultFound : SearchMovieUiState()
}
