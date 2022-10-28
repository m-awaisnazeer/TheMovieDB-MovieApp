package com.example.awaisahmadassignment.common.data.api.model.mappers

import com.example.awaisahmadassignment.common.data.api.model.ApiMovie
import com.example.awaisahmadassignment.common.data.cache.model.CachedMovie
import com.example.awaisahmadassignment.common.domain.model.Movie

fun ApiMovie.toDomain(): Movie {
    return Movie(
        id = id?: throw Exception("Movie ID cannot be null"),
        posterPath = poster_path.orEmpty(),
        releaseDate = release_date.orEmpty(),
        title = title.orEmpty(),
        isFavorite = false
    )
}

fun ApiMovie.ToCachedMovie(): CachedMovie {
    return CachedMovie(
        id = id?: throw Exception("Movie ID cannot be null"),
        posterPath = poster_path.orEmpty(),
        releaseDate = release_date.orEmpty(),
        title = title.orEmpty(),
        isFavorite = false
    )
}