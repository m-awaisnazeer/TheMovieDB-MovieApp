package com.example.themoviedb.moviedetail.presentation

import com.example.themoviedb.common.domain.model.Movie

sealed class MovieDetailsEvent{
    data class LoadMovie(val movie: Movie):MovieDetailsEvent()
    data class AddToFavorites(val movie: Movie):MovieDetailsEvent()
}
