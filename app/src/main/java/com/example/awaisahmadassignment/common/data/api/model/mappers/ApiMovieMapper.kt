package com.example.awaisahmadassignment.common.data.api.model.mappers

import com.example.awaisahmadassignment.common.data.api.model.ApiMovie
import com.example.awaisahmadassignment.common.data.cache.model.CachedMovie

fun ApiMovie.toCachedMovie(): CachedMovie {
    return CachedMovie(
        posterPath = poster_path.orEmpty(),
        releaseDate = release_date.orEmpty(),
        title = title.orEmpty(),
        isFavorite = false
    )
}