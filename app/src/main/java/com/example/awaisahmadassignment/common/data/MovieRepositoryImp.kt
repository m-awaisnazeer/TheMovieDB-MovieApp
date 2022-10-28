package com.example.awaisahmadassignment.common.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.awaisahmadassignment.common.data.api.TheMovieDbApi
import com.example.awaisahmadassignment.common.data.cache.MovieDatabase
import com.example.awaisahmadassignment.common.data.cache.model.CachedMovie
import com.example.awaisahmadassignment.common.domain.repositories.MovieRepository
import com.example.awaisahmadassignment.common.pagging.MovieRemoteMediator
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
class MovieRepositoryImp(
    private val api: TheMovieDbApi,
    private val movieDB: MovieDatabase
) : MovieRepository {

    override fun getMovies(): Flow<PagingData<CachedMovie>> = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        remoteMediator = MovieRemoteMediator(api, movieDB),
        pagingSourceFactory = { movieDB.movieDao().getMovies() }
    ).flow

}