package com.example.desafioemjetpackcompose.data.data_sources

import com.example.desafioemjetpackcompose.core.exceptions.ResourceNotFoundThrowable
import com.example.desafioemjetpackcompose.data.models.MovieModel

class MovieLocalDataSourceImpl: MovieLocalDataSource {

    override suspend fun cacheDetailMovie(movie: MovieModel) {
        InCacheDao.cacheMovie(movie)
    }

    override suspend fun getCachedDetailMovie(): MovieModel {
        return InCacheDao.getChachedMovie() ?: throw ResourceNotFoundThrowable()
    }

    override suspend fun cacheFavoriteMovies(movies: MutableList<MovieModel>) {
        InCacheDao.setCachedFavoriteMovies(movies.toMutableList())
    }

    override suspend fun getCachedFavoriteMovies(filter: String): MutableList<MovieModel> {
        return InCacheDao.getCachedFavoriteMovies().filter {
            it.title.contains(filter, ignoreCase = true)
        }.toMutableList()
    }
}