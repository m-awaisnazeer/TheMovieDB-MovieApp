package com.example.awaisahmadassignment.common.domain.repositories

import androidx.paging.PagingData
import com.example.awaisahmadassignment.common.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(): Flow<PagingData<Movie>>
}