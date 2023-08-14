package com.applications.details.presentation

import com.applications.domain.entities.Movie

sealed class MovieUIState {
    object Loading : MovieUIState()
    data class MovieDetails(val movie: Movie) : MovieUIState()
}
