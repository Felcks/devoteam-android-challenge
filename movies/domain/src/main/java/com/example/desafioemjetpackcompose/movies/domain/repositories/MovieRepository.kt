package com.example.desafioemjetpackcompose.movies.domain.repositories

import com.example.desafioemjetpackcompose.movies.domain.models.Genre
import com.example.desafioemjetpackcompose.movies.domain.models.Movie

interface MovieRepository {
   suspend fun getAllMovies(page: Int = 1): List<Movie>
   suspend fun getAllMoviesGenres(): List<Genre>

   suspend fun cacheDetailMovie(movie: Movie)
   suspend fun getCachedDetailMovie(): Movie

   suspend fun favoriteOrDisfavorMovie(movie: Movie): Movie
   suspend fun getFavoriteMovies(filter: String = ""): List<Movie>
}