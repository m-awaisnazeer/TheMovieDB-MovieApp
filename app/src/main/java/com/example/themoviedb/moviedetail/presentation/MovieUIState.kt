package com.example.themoviedb.moviedetail.presentation

import com.applications.domain.domain.entities.Movie

sealed class MovieUIState {
    object Loading : MovieUIState()
    data class MovieDetails(val movie: Movie) : MovieUIState()
}
