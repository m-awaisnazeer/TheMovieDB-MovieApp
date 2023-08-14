package com.applications.network.model.mappers

import com.applications.network.model.ApiMovie
import com.applications.database.model.CachedMovie
import com.applications.domain.domain.entities.Movie

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