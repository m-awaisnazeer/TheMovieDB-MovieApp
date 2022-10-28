package com.example.awaisahmadassignment.moviedetail.presentation

import com.example.awaisahmadassignment.common.domain.model.Movie

sealed class MovieUIState {
    object Loading : MovieUIState()
    data class Success(val movie: Movie) : MovieUIState()
}
