package com.example.themoviedb.search.domain

import com.example.themoviedb.common.domain.repositories.MovieRepository
import javax.inject.Inject

class SearchMovies @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(query: String) = movieRepository.searchMovie(query)
}