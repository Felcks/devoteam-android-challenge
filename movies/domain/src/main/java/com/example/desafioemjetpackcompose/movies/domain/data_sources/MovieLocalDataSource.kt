package com.example.desafioemjetpackcompose.movies.domain.data_sources

import com.example.desafioemjetpackcompose.movies.domain.models.Movie

interface MovieLocalDataSource {

    suspend fun cacheDetailMovie(movie: Movie)
    suspend fun getCachedDetailMovie(): Movie

    suspend fun cacheFavoriteMovies(movies: MutableList<Movie>)
    suspend fun getCachedFavoriteMovies(filter: String = ""): MutableList<Movie>
}