package com.example.themoviedb.favorites.presentation

import com.example.themoviedb.common.domain.entities.Movie

sealed class FavoritesMovieEvent{
    data class AddToFavorites(val movie: Movie):FavoritesMovieEvent()
}