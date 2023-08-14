package com.example.themoviedb.moviedetail.domain

import com.applications.domain.domain.repositories.MovieRepository
import javax.inject.Inject

class GetMovie @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(movieId:Int) = repository.getMovieById(movieId)
}