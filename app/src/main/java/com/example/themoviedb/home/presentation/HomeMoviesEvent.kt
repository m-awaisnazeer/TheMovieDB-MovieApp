package com.example.themoviedb.home.presentation

import com.example.themoviedb.common.domain.entities.Movie

sealed class HomeMoviesEvent{
    data class AddToFavorites(val movie: Movie):HomeMoviesEvent()
}
