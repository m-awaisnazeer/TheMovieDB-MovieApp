package com.example.themoviedb.common.data.api.model.mappers

import com.example.themoviedb.common.data.api.model.ApiMovie
import com.example.themoviedb.common.data.cache.model.CachedMovie

fun ApiMovie.toCachedMovie(): CachedMovie {
    return CachedMovie(
        posterPath = posterPath.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        title = title.orEmpty(),
        overView = overview.orEmpty(),
        isFavorite = false
    )
}