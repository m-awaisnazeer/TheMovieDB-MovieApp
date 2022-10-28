package com.example.themoviedb.common.data.api.model

import com.google.gson.annotations.SerializedName

data class ApiMoviesList(
    val page: Int?,
    @SerializedName("results")
    val apiMovies: List<ApiMovie>,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)