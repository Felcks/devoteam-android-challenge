package com.example.desafioemjetpackcompose.data.data_sources

import android.util.Log
import com.example.desafioemjetpackcompose.data.models.MovieModel

object InCacheDao {

    private var movieDetail: MovieModel? = null
    fun getChachedMovie(): MovieModel? = this.movieDetail
    fun cacheMovie(movie: MovieModel) {
        this.movieDetail = movie
    }

    private var favoriteMovies = mutableListOf<MovieModel>()
    fun getCachedFavoriteMovies() = favoriteMovies
    fun setCachedFavoriteMovies(list: MutableList<MovieModel>) {
        this.favoriteMovies = list
    }
}