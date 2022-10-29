package com.example.themoviedb.favorites.presentation

import com.example.themoviedb.common.domain.model.Movie

sealed class FavoritesMovieEvent{
    data class AddToFavorites(val movie: Movie):FavoritesMovieEvent()
}