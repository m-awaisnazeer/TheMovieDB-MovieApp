package com.example.awaisahmadassignment.home

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.awaisahmadassignment.common.data.cache.model.CachedMovie
import com.example.awaisahmadassignment.common.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow

class HomeViewModel(private val repository: MovieRepository) : ViewModel() {

    val list: Flow<PagingData<CachedMovie>> = repository.getMovies()

}