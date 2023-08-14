package com.example.themoviedb.search.presentation

import com.applications.domain.domain.entities.Movie

sealed class SearchMovieUiState {
    data class Error(val message: String) : SearchMovieUiState()
    object Loading : SearchMovieUiState()
    object Initial : SearchMovieUiState()
    data class Success(val data: List<Movie>) : SearchMovieUiState()
    object NoResultFound : SearchMovieUiState()
}
