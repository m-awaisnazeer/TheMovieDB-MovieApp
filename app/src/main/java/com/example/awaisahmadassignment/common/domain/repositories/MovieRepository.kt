package com.example.awaisahmadassignment.common.domain.repositories

import androidx.paging.PagingData
import com.example.awaisahmadassignment.common.data.cache.model.CachedMovie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(): Flow<PagingData<CachedMovie>>
}