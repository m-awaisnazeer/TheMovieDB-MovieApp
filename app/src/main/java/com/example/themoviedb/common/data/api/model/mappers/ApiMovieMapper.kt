package com.example.themoviedb.common.data.api.model.mappers

import com.example.themoviedb.common.data.api.model.ApiMovie
import com.example.themoviedb.common.data.cache.model.CachedMovie
import com.example.themoviedb.common.domain.entities.Movie

fun ApiMovie.toCachedMovie(): CachedMovie {
    return CachedMovie(
        posterPath = posterPath.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        title = title.orEmpty(),
        overView = overview.orEmpty(),
        isFavorite = false
    )
}

fun ApiMovie.toDomainMovie(): Movie {
    return Movie(
        id = id ?: throw Exception("Id can't be null"),
        posterPath = posterPath.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        title = title.orEmpty(),
        overView = overview.orEmpty(),
        isFavorite = false
    )
}