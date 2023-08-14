package com.example.themoviedb.home.presentation

import com.applications.domain.domain.entities.Movie

sealed class HomeMoviesEvent{
    data class AddToFavorites(val movie: Movie):HomeMoviesEvent()
}
