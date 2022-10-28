package com.example.themoviedb.moviedetail.domain

import com.example.themoviedb.common.domain.repositories.MovieRepository

class GetMovie(
    private val repository: MovieRepository
) {
    operator fun invoke(movieId:Int)= repository.getMovieById(movieId)
}