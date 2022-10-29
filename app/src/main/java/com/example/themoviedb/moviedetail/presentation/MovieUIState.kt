package com.example.themoviedb.moviedetail.presentation

import com.example.themoviedb.common.domain.model.Movie

sealed class MovieUIState {
    object Loading : MovieUIState()
    data class MovieDetails(val movie: Movie) : MovieUIState()
}
