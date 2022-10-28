package com.example.awaisahmadassignment.common.data.api.model.mappers

import com.example.awaisahmadassignment.common.data.api.model.ApiMovie
import com.example.awaisahmadassignment.common.data.cache.model.CachedMovie

fun ApiMovie.toCachedMovie(): CachedMovie {
    return CachedMovie(
        posterPath = posterPath.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        title = title.orEmpty(),
        overView = overview.orEmpty(),
        isFavorite = false
    )
}