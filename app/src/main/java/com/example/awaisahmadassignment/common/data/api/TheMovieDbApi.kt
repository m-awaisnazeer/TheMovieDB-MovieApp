package com.example.awaisahmadassignment.common.data.api

import com.example.awaisahmadassignment.common.data.api.model.ApiMoviesList
import com.example.awaisahmadassignment.common.utils.Constants.API_KEY
import com.example.awaisahmadassignment.common.utils.Constants.MOVIES_LIST_END_URL
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDbApi {

    @GET(MOVIES_LIST_END_URL)
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int
    ): ApiMoviesList
}