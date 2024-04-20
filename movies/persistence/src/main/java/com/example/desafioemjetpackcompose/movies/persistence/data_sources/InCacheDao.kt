package com.example.desafioemjetpackcompose.movies.persistence.data_sources

import com.example.desafioemjetpackcompose.movies.domain.models.Movie

object InCacheDao {

    private var movieDetail: Movie? = null
    fun getChachedMovie(): Movie? = movieDetail
    fun cacheMovie(movie: Movie) {
        movieDetail = movie
    }

    private var favoriteMovies = mutableListOf<Movie>()
    fun getCachedFavoriteMovies() = favoriteMovies
    fun setCachedFavoriteMovies(list: MutableList<Movie>) {
        favoriteMovies = list
    }
}