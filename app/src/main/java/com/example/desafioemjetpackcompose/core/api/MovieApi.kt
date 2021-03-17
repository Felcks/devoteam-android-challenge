package com.example.desafioemjetpackcompose.core.api

import com.example.desafioemjetpackcompose.data.models.GenreResultModel
import com.example.desafioemjetpackcompose.data.models.MovieModel
import com.example.desafioemjetpackcompose.data.models.PageModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getAllMovies(
        @Query(value = "api_key") apiKey: String,
        @Query(value = "page") page: Int = 1
    ): Response<PageModel<MovieModel>>

    @GET("genre/movie/list")
    suspend fun getAllMoviesGenres(@Query(value = "api_key") apiKey: String): Response<GenreResultModel>
}