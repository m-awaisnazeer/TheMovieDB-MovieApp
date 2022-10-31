package com.example.themoviedb.common.data

import android.content.Context
import androidx.room.Room
import com.example.themoviedb.common.data.cache.MovieDatabase
import com.example.themoviedb.common.data.cache.model.CachedMovie
import com.example.themoviedb.common.domain.entities.Movie

object FakeDataFactory {

    fun cachedMovie1() = CachedMovie(
        1, "posterPath", "releaseDate", "title", "overView", false
    )

    private fun cachedMovie2() = CachedMovie(
        2, "posterPath2", "releaseDate2", "title2", "overView2", true
    )

    fun movie2UnFavorite() = Movie(
        2, "posterPath2", "releaseDate2", "title2", "overView2", false
    )

    val fakeMovies = listOf(
        cachedMovie1(), cachedMovie2()
    )

    fun movieDatabase(context: Context): MovieDatabase {
        return Room.inMemoryDatabaseBuilder(context, MovieDatabase::class.java)
            .allowMainThreadQueries().build()
    }

//    val fakeApiResponse: ApiMoviesList = ApiMoviesList(
//        1,
//        listOf(
//            ApiMovie(
//                false, "", listOf(1), 11, "lang", "title",
//                "overview", 1.11, "path", "release", "title",
//                false, 2.2, 22
//            )
//        ), 1, 1
//    )
}