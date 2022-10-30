package com.example.themoviedb.common.data.cache.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.example.themoviedb.common.data.FakeDataFactory.cachedMovie1
import com.example.themoviedb.common.data.FakeDataFactory.fakeMovies
import com.example.themoviedb.common.data.cache.MovieDatabase
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class MovieDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var movieDatabase: MovieDatabase
    private lateinit var context: Context
    private lateinit var movieDao: MovieDao

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        movieDatabase = Room.inMemoryDatabaseBuilder(context, MovieDatabase::class.java)
            .allowMainThreadQueries().build()
        movieDao = movieDatabase.movieDao()
    }

    @After
    fun tearDown() {
        movieDatabase.close()
    }

    @Test
    fun `MovieDao inserts Single Movie into Database with Success`() = runTest {
        movieDao.insert(cachedMovie1())
        movieDao.getMovieById(1).test {
            val movie = awaitItem()
            Truth.assertThat(movie.id).isEqualTo(1)
        }
    }

    @Test
    fun `MovieDao inserts Multiple Movie items into Database with Success`() = runTest {
        val movies = fakeMovies
        movieDao.insert(movies)
        Truth.assertThat(movieDao.getAllMovies().size).isEqualTo(movies.size)
    }

    @Test
    fun `getAllMovies returns zero movies without any insertions`() = runTest {
        Truth.assertThat(movieDao.getAllMovies().size).isEqualTo(0)
    }

    @Test
    fun `getAllMovies returns movies inserted into Database`() = runTest {
        movieDao.insert(fakeMovies)
        Truth.assertThat(movieDao.getAllMovies().size).isEqualTo(fakeMovies.size)
    }

    @Test
    fun `movieDao delete All Movies from Database`() = runTest {
        movieDao.insert(fakeMovies)
        movieDao.deleteAllMovies()
        Truth.assertThat(movieDao.getAllMovies().size).isEqualTo(0)
    }

    @Test
    fun `MovieDao returns All Favorite Movies from Database`() = runTest {
        `MovieDao inserts Multiple Movie items into Database with Success`()
        movieDao.getFavoriteMovies(true).test {
            val movies = awaitItem()
            Truth.assertThat(movies.size).isEqualTo(1)
        }
    }

    @Test
    fun `MovieDao returns All UnFavorite Movies from Database`() = runTest {
        `MovieDao inserts Multiple Movie items into Database with Success`()
        movieDao.getFavoriteMovies(false).test {
            val movies = awaitItem()
            Truth.assertThat(movies.size).isEqualTo(1)
        }
    }

    @Test
    fun `MovieDao returns Zero Favorite Movies when Database is empty `() = runTest {
        movieDao.getFavoriteMovies(true).test {
            val movies = awaitItem()
            Truth.assertThat(movies.size).isEqualTo(0)
        }
    }

    @Test
    fun `MovieDao update Movie when Movie Record exists in Database`() {
        `MovieDao inserts Multiple Movie items into Database with Success`()
        val result = movieDao.updateMovie(1, true)
        Truth.assertThat(result).isEqualTo(1)
    }

    @Test
    fun `MovieDao don't update Movie when Movie Record don't exists in Database`() {
        val result = movieDao.updateMovie(111, true)
        Truth.assertThat(result).isEqualTo(0)
    }

    @Test
    fun `MovieDao return movie from Database when id matches`() = runTest {
        `MovieDao inserts Multiple Movie items into Database with Success`()
        movieDao.getMovieById(2).test {
            val movie = awaitItem()
            Truth.assertThat(movie).isEqualTo(fakeMovies.last())
        }
    }

    @Test
    fun `movieDao don't return movie from Database when id don't matches`() = runTest {
        movieDao.getMovieById(12).test {
            val movie = awaitItem()
            Truth.assertThat(movie).isEqualTo(null)
        }
    }

}