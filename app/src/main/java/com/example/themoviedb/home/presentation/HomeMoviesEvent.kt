package com.example.themoviedb.home.presentation

import com.example.themoviedb.common.domain.model.Movie

sealed class HomeMoviesEvent{
    data class AddToFavorites(val movie: Movie):HomeMoviesEvent()
}
