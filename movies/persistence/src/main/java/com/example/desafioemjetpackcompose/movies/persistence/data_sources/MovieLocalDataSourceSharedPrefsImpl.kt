package com.example.desafioemjetpackcompose.movies.persistence.data_sources

import android.content.SharedPreferences
import com.example.desafioemjetpackcompose.movies.domain.data_sources.MovieLocalDataSource
import com.example.desafioemjetpackcompose.movies.domain.exceptions.ResourceNotFoundThrowable
import com.example.desafioemjetpackcompose.movies.domain.models.Movie
import com.google.gson.Gson

class MovieLocalDataSourceSharedPrefsImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : MovieLocalDataSource {


    override suspend fun cacheDetailMovie(movie: Movie) {
        InCacheDao.cacheMovie(movie)
    }

    override suspend fun getCachedDetailMovie(): Movie {
        return InCacheDao.getChachedMovie() ?: throw ResourceNotFoundThrowable()
    }

    override suspend fun cacheFavoriteMovies(movies: MutableList<Movie>) {
        val editor = sharedPreferences.edit()

        val hashSet = movies.map { gson.toJson(it) }.toHashSet()
        editor.putStringSet(SHARED_PREFERENCES_FAVORITE_MOVIES_KEY, hashSet)
        editor.apply()
    }

    override suspend fun getCachedFavoriteMovies(filter: String): MutableList<Movie> {
        var hashSet = hashSetOf<String>()
        hashSet = sharedPreferences.getStringSet(
            SHARED_PREFERENCES_FAVORITE_MOVIES_KEY,
            hashSet
        ) as HashSet<String>

        return hashSet.map {
            gson.fromJson(it, Movie::class.java)
        }.filter {
            it.title.contains(filter, ignoreCase = true)
        }.filter {
            it.isFavorite
        }.toMutableList()
    }

    companion object {
        const val SHARED_PREFERENCES_FAVORITE_MOVIES_KEY =
            "com.example.desafioemjetpackcompose.sharedpreferences.favorite_movies"
        const val SHARED_PREFERENCES_KEY = "com.example.desafioemjetpackcompose.sharedpreferences"
    }
}