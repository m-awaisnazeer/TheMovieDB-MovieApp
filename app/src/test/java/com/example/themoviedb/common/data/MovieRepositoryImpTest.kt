package com.example.themoviedb.common.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.example.themoviedb.common.data.api.TheMovieDbApi
import com.example.themoviedb.common.data.cache.MovieDatabase
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class MovieRepositoryImpTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: MovieRepositoryImp
    private lateinit var movieDatabase: MovieDatabase
    private lateinit var context: Context
    private lateinit var api: TheMovieDbApi

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        movieDatabase = FakeDataFactory.movieDatabase(context)
        api = mock()
        repository = MovieRepositoryImp(api, movieDatabase)
    }

    @After
    fun tearDown() {
        movieDatabase.close()
    }

    @Test
    fun `getFavoriteMovies return favorites movies from local DB`() = runTest {
        movieDatabase.movieDao().insert(FakeDataFactory.fakeMovies)
        repository.getFavoriteMovies().test {
            val movies = awaitItem()
            Truth.assertThat(movies.last().isFavorite).isEqualTo(true)
        }
    }

    @Test
    fun `updateMovie update existing movie from DB`() = runTest {
        movieDatabase.movieDao().insert(FakeDataFactory.fakeMovies)
        repository.updateMovie(FakeDataFactory.movie2UnFavorite())
        repository.getMovieById(2).test {
            val cachedMovie = awaitItem()
            Truth.assertThat(cachedMovie.isFavorite).isFalse()
        }
    }

    @Test
    fun `updateMovie inserts movie into DB when didn't exists in DB`() = runTest {
        repository.updateMovie(FakeDataFactory.movie2UnFavorite())
        repository.getMovieById(1).test {
            val cachedMovie = awaitItem()
            Truth.assertThat(cachedMovie.isFavorite).isFalse()
        }
    }

    @Test
    fun `getMovieById return movie from Local DB when Movie ID matched`() = runTest {
        movieDatabase.movieDao().insert(FakeDataFactory.fakeMovies)
        repository.getMovieById(1).test {
            val cachedMovie = awaitItem()
            Truth.assertThat(cachedMovie.isFavorite).isFalse()
        }
    }
}