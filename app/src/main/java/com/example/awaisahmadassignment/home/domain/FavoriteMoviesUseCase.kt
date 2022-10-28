package com.example.awaisahmadassignment.home.domain

import com.example.awaisahmadassignment.common.domain.repositories.MovieRepository

class FavoriteMoviesUseCase(
    private val repository: MovieRepository
) {

    operator fun invoke(movieId:Int,isFavorite:Boolean){
        repository.updateMovie(movieId,isFavorite)
    }
}