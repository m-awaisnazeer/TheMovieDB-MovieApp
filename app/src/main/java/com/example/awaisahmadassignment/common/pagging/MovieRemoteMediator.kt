package com.example.awaisahmadassignment.common.pagging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.awaisahmadassignment.common.data.api.TheMovieDbApi
import com.example.awaisahmadassignment.common.data.api.model.mappers.toCachedMovie
import com.example.awaisahmadassignment.common.data.cache.MovieDatabase
import com.example.awaisahmadassignment.common.data.cache.model.CachedMovie
import com.example.awaisahmadassignment.common.data.cache.model.MovieRemoteKeys

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val api: TheMovieDbApi, private val movieDB: MovieDatabase
) : RemoteMediator<Int, CachedMovie>() {
    private val movieDao = movieDB.movieDao()
    private val remoteKeysDao = movieDB.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, CachedMovie>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosetToCurrentPosition(state)
                    Log.d(
                        MovieRemoteMediator::class.simpleName,
                        "REFRESH" + "remoteKeys: ${remoteKeys?.nextPage?.minus(1) ?: 1}"
                    )
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    Log.d(
                        MovieRemoteMediator::class.simpleName,
                        "PREPEND" + "remoteKeys: ${remoteKeys.prevPage}"
                    )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    Log.d(
                        MovieRemoteMediator::class.simpleName,
                        "APPEND" + "remoteKeys: ${remoteKeys.nextPage}" + "id: ${remoteKeys.id}"
                    )
                    nextPage
                }
            }
            val response = api.getPopularMovies(page = currentPage)
            val endOfPaginationReached = response.totalPages == currentPage
            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1
            movieDB.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        movieDao.deleteAllMovies()
                        remoteKeysDao.deleteAllRemoteKeys()
                    }
                    val cachedMovies = response.apiMovies.map { it.toCachedMovie() }
                    movieDao.insert(cachedMovies)
                    val keys = movieDao.getAllMovies().map { movie ->
                        MovieRemoteKeys(
                            id = movie.id, nextPage = nextPage, prevPage = prevPage
                        )
                    }
                    remoteKeysDao.addAllRemoteKeys(keys)
                    MediatorResult.Success(endOfPaginationReached)
                }

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosetToCurrentPosition(
        state: PagingState<Int, CachedMovie>
    ): MovieRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeysDao.getRemoteKeys(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, CachedMovie>
    ): MovieRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let {
            remoteKeysDao.getRemoteKeys(id = it.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, CachedMovie>
    ): MovieRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let {
            remoteKeysDao.getRemoteKeys(id = it.id)
        }
    }
}