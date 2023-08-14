package com.applications.details.presentation

import com.applications.domain.entities.Movie

sealed class MovieDetailsEvent{
    data class LoadMovie(val movie: Movie): MovieDetailsEvent()
    data class AddToFavorites(val movie: Movie): MovieDetailsEvent()
}
