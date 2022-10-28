package com.example.awaisahmadassignment.moviedetail.domain

import com.example.awaisahmadassignment.common.domain.repositories.MovieRepository

class GetMovie(
    private val repository: MovieRepository
) {
    operator fun invoke(movieId:Int)= repository.getMovieById(movieId)
}