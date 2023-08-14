package com.applications.network

import com.applications.network.model.ApiMoviesList
import com.applications.utils.Constants.API_KEY
import com.applications.utils.Constants.MOVIES_LIST_END_URL
import com.applications.utils.Constants.SEARCH_MOVIES_END_URL
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDbApi {

    @GET(MOVIES_LIST_END_URL)
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int
    ): ApiMoviesList

    @GET(SEARCH_MOVIES_END_URL)
    suspend fun searchMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") query:String,
        @Query("page") page: Int
    ): ApiMoviesList?
}