package com.example.desafioemjetpackcompose.movies.domain.data_sources

import com.example.desafioemjetpackcompose.movies.domain.models.Genre
import com.example.desafioemjetpackcompose.movies.domain.models.Movie

interface MovieRemoteDataSource {

    suspend fun getAllMovies(page: Int = 1): List<Movie>
    suspend fun getAllMoviesGenres(): List<Genre>
}