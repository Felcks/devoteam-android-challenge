package com.example.desafioemjetpackcompose.data.data_sources

import com.example.desafioemjetpackcompose.data.models.MovieModel
import com.example.desafioemjetpackcompose.domain.entities.Movie

interface MovieLocalDataSource {

    suspend fun cacheDetailMovie(movie: MovieModel)
    suspend fun getCachedDetailMovie(): MovieModel

    suspend fun cacheFavoriteMovies(movies: MutableList<MovieModel>)
    suspend fun getCachedFavoriteMovies(filter: String = ""): MutableList<MovieModel>
}