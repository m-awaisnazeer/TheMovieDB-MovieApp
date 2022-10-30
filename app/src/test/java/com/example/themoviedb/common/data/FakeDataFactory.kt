package com.example.themoviedb.common.data

import com.example.themoviedb.common.data.cache.model.CachedMovie

object FakeDataFactory {

    fun cachedMovie1() = CachedMovie(
        1, "posterPath", "releaseDate", "title", "overView", false
    )

    private fun cachedMovie2() = CachedMovie(
        2, "posterPath2", "releaseDate2", "title2", "overView2", true
    )

    val fakeMovies = listOf(
        cachedMovie1(), cachedMovie2()
    )
}