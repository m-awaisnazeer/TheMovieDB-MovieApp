package com.example.awaisahmadassignment.common.data.cache

import com.example.awaisahmadassignment.common.data.api.model.ApiMoviesList

interface Cache {
    suspend fun storeMovies(apiMoviesList: ApiMoviesList)
}