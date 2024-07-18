package com.example.emergetestapplication.emerge.data.network

import com.example.emergetestapplication.emerge.data.model.movies.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
    ): MovieResponse
}
