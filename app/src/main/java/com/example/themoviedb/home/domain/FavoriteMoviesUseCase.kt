package com.example.themoviedb.home.domain

import com.example.themoviedb.common.domain.repositories.MovieRepository

class FavoriteMoviesUseCase(
    private val repository: MovieRepository
) {

    operator fun invoke(movieId:Int,isFavorite:Boolean){
        repository.updateMovie(movieId,isFavorite)
    }
}