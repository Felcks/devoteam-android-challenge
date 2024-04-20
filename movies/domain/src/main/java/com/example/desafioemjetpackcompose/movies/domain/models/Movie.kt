package com.example.desafioemjetpackcompose.movies.domain.models

import java.util.Date

interface Movie {
    val id: Int
    val originalTitle: String
    val originalLanguage: String
    val title: String
    val posterPath: String
    val isAdult: Boolean
    val overview: String
    val releaseDate: Date
    val popularity: Double
    val voteCount: Int
    val video: Boolean
    val voteAverage: Double
    val backdropPath: String
    var genreIds: List<Int>
    var isFavorite: Boolean

    fun getGenres(allGenreUIModels: List<Genre>): List<Genre>
    fun getGenresText(allGenreUIModels: List<Genre>): String
    fun getPosterCompleteUrl(): String
    fun getBackdropCompleteUrl(): String
}