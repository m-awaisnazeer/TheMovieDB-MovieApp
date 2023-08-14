package com.example.themoviedb.favorites.presentation

import com.applications.domain.entities.Movie

sealed class FavoritesMovieEvent{
    data class AddToFavorites(val movie: Movie):FavoritesMovieEvent()
}