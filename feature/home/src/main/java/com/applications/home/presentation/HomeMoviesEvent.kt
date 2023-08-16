package com.applications.home.presentation

import com.applications.domain.entities.Movie

sealed class HomeMoviesEvent{
    data class AddToFavorites(val movie: Movie): HomeMoviesEvent()
}
