package com.example.desafioemjetpackcompose.movies.network.api

import com.example.desafioemjetpackcompose.movies.domain.data_sources.PageModel
import com.example.desafioemjetpackcompose.movies.domain.models.Movie
import com.example.desafioemjetpackcompose.movies.network.models.GenreResultModel
import com.example.desafioemjetpackcompose.movies.network.models.MovieModel
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