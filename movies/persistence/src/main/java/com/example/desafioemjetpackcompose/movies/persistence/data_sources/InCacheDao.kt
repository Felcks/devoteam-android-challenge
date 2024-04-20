package com.example.desafioemjetpackcompose.movies.persistence.data_sources

import com.example.desafioemjetpackcompose.movies.domain.models.Movie
import com.example.desafioemjetpackcompose.movies.persistence.entities.MovieEntity

object InCacheDao {

    private var movieDetail: MovieEntity? = null
    fun getChachedMovie(): Movie? = movieDetail
    fun cacheMovie(movie: Movie) {
        movieDetail = MovieEntity.fromDomain(movie)
    }

    private var favoriteMovies = mutableListOf<MovieEntity>()
    fun getCachedFavoriteMovies() = favoriteMovies
    fun setCachedFavoriteMovies(list: MutableList<Movie>) {
        favoriteMovies = list.map { MovieEntity.fromDomain(it) }.toMutableList()
    }
}