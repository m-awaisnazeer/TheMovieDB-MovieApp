package com.example.themoviedb.common.data

import androidx.paging.*
import com.example.themoviedb.common.data.api.TheMovieDbApi
import com.example.themoviedb.common.data.api.model.mappers.toDomainMovie
import com.example.themoviedb.common.data.cache.MovieDatabase
import com.example.themoviedb.common.data.cache.model.CachedMovie
import com.example.themoviedb.common.data.cache.model.CachedMovie.Companion.toDomain
import com.example.themoviedb.common.domain.entities.Movie
import com.example.themoviedb.common.domain.repositories.MovieRepository
import com.example.themoviedb.common.pagging.MovieRemoteMediator
import com.example.themoviedb.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MovieRepositoryImp @Inject constructor(
    val api: TheMovieDbApi,
    private val movieDatabase: MovieDatabase
) : MovieRepository {

    override fun getMovies(): Flow<PagingData<Movie>> =
        Pager(config = PagingConfig(pageSize = 20, maxSize = 100),
            remoteMediator = MovieRemoteMediator(api, movieDatabase),
            pagingSourceFactory = { movieDatabase.movieDao().getMovies() }).flow.map {
            it.map { cachedMovie ->
                Movie(
                    id = cachedMovie.id,
                    posterPath = cachedMovie.posterPath,
                    releaseDate = cachedMovie.releaseDate,
                    title = cachedMovie.title,
                    overView = cachedMovie.overView,
                    isFavorite = cachedMovie.isFavorite
                )
            }
        }

    override fun getFavoriteMovies(): Flow<List<Movie>> = flow {
        val cachedMovies = movieDatabase.movieDao().getFavoriteMovies(true)
        cachedMovies.collect {
            emit(it.map { toDomain(it) })
        }
    }

    override suspend fun updateMovie(movie: Movie) {
        movie.apply {
            val update: Int = movieDatabase.movieDao().updateMovie(id, isFavorite)
            if (update == 0) {
                movieDatabase.movieDao().insert(
                    CachedMovie(
                        posterPath = posterPath,
                        releaseDate = releaseDate,
                        title = title,
                        overView = overView,
                        isFavorite = isFavorite
                    )
                )
            }
        }
    }

    override fun getMovieById(movieId: Int): Flow<Movie> =
        movieDatabase.movieDao().getMovieById(movieId).map {
            toDomain(it)
        }

    override fun searchMovie(query: String): Flow<Resource<List<Movie>>> = flow {
        try {
            val response = api.searchMovies(query = query, page = 1)
            response?.apiMovies?.let {
                val movies: List<Movie> = it.map { it.toDomainMovie() }
                emit(Resource.Success(movies))
            }
        } catch (e: HttpException) {
            emit(Resource.Error(null, e.message()))
        } catch (e: IOException) {
            emit(Resource.Error(null, e.message ?: "Network connection Error"))
        }
    }

}