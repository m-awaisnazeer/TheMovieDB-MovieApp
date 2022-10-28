package com.example.themoviedb.common.data.cache

import com.example.themoviedb.common.data.api.model.ApiMoviesList

interface Cache {
    suspend fun storeMovies(apiMoviesList: ApiMoviesList)
}