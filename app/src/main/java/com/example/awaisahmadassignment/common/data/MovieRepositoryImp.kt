package com.example.awaisahmadassignment.common.data

import androidx.paging.*
import com.example.awaisahmadassignment.common.data.api.TheMovieDbApi
import com.example.awaisahmadassignment.common.data.cache.MovieDatabase
import com.example.awaisahmadassignment.common.data.cache.model.CachedMovie
import com.example.awaisahmadassignment.common.domain.model.Movie
import com.example.awaisahmadassignment.common.domain.repositories.MovieRepository
import com.example.awaisahmadassignment.common.pagging.MovieRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class MovieRepositoryImp(
    private val api: TheMovieDbApi, private val movieDB: MovieDatabase
) : MovieRepository {

    override fun getMovies(): Flow<PagingData<Movie>> =
        Pager(config = PagingConfig(pageSize = 20, maxSize = 100),
            remoteMediator = MovieRemoteMediator(api, movieDB),
            pagingSourceFactory = { movieDB.movieDao().getMovies() }).flow.map {
            it.map { cachedMovie ->
                Movie(
                    id = cachedMovie.id,
                    posterPath = cachedMovie.posterPath,
                    releaseDate = cachedMovie.releaseDate,
                    title = cachedMovie.title,
                    isFavorite = cachedMovie.isFavorite
                )
            }
        }

    override fun getFavoriteMovies(): Flow<List<Movie>> = flow {
        val cachedMovies = movieDB.movieDao().getFavoriteMovie(true)
        cachedMovies.collect {
            emit(it.map { CachedMovie.toDomain(it) })
        }
    }

    override fun updateMovie(movieId: Int, isFavorite: Boolean) {
        movieDB.movieDao().updateMovie(movieId,isFavorite)
    }

}