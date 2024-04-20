package com.example.desafioemjetpackcompose.movies.persistence.data_sources

import com.example.desafioemjetpackcompose.movies.domain.exceptions.ResourceNotFoundThrowable
import com.example.desafioemjetpackcompose.movies.domain.data_sources.MovieLocalDataSource
import com.example.desafioemjetpackcompose.movies.domain.models.Movie

class MovieLocalDataSourceImpl: MovieLocalDataSource {

    override suspend fun cacheDetailMovie(movie: Movie) {
        InCacheDao.cacheMovie(movie)
    }

    override suspend fun getCachedDetailMovie(): Movie {
        return InCacheDao.getChachedMovie() ?: throw ResourceNotFoundThrowable()
    }

    override suspend fun cacheFavoriteMovies(movies: MutableList<Movie>) {
        InCacheDao.setCachedFavoriteMovies(movies.toMutableList())
    }

    override suspend fun getCachedFavoriteMovies(filter: String): MutableList<Movie> {
        return InCacheDao.getCachedFavoriteMovies().filter {
            it.title.contains(filter, ignoreCase = true)
        }.toMutableList()
    }
}