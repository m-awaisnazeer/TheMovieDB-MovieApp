package com.example.themoviedb.search.domain

import com.example.themoviedb.common.domain.repositories.MovieRepository

class SearchMovies(private val movieRepository: MovieRepository) {

    operator fun invoke(query: String) = movieRepository.searchMovie(query)
}