package com.example.desafioemjetpackcompose.data.data_sources

import com.example.desafioemjetpackcompose.data.models.GenreResultModel
import com.example.desafioemjetpackcompose.data.models.MovieModel
import com.example.desafioemjetpackcompose.data.models.PageModel

interface MovieRemoteDataSource {

    suspend fun getAllMovies(page: Int = 1): PageModel<MovieModel>
    suspend fun getAllMoviesGenres(): GenreResultModel
}