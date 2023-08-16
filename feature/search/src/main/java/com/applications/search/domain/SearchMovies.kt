package com.applications.search.domain

import com.applications.domain.repositories.MovieRepository
import javax.inject.Inject

class SearchMovies @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(query: String) = movieRepository.searchMovie(query)
}