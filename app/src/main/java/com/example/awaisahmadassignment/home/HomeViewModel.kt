package com.example.awaisahmadassignment.home

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.awaisahmadassignment.common.domain.model.Movie
import com.example.awaisahmadassignment.common.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow

class HomeViewModel(repository: MovieRepository) : ViewModel() {

    val list: Flow<PagingData<Movie>> = repository.getMovies()

}