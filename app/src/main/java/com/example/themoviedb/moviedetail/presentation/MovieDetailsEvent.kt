package com.example.themoviedb.moviedetail.presentation

import com.applications.domain.domain.entities.Movie

sealed class MovieDetailsEvent{
    data class LoadMovie(val movie: Movie):MovieDetailsEvent()
    data class AddToFavorites(val movie: Movie):MovieDetailsEvent()
}
