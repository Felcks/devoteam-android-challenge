package com.example.desafioemjetpackcompose.movies.persistence.entities

import com.example.desafioemjetpackcompose.movies.domain.models.Genre
import com.example.desafioemjetpackcompose.movies.domain.models.Movie
import java.util.Date

class MovieEntity(
    override val id: Int,
    override val originalTitle: String,
    override val originalLanguage: String,
    override val title: String,
    override val posterPath: String,
    override val isAdult: Boolean,
    override val overview: String,
    override val releaseDate: Date,
    override val popularity: Double,
    override val voteCount: Int,
    override val video: Boolean,
    override val voteAverage: Double,
    override val backdropPath: String,
    override var genreIds: List<Int>,
    override var isFavorite: Boolean
) : Movie {

    companion object {
        fun fromDomain(movie: Movie) : MovieEntity {
            return with(movie) {
                MovieEntity(id, originalTitle, originalLanguage, title, posterPath, isAdult, overview, releaseDate, popularity, voteCount, video, voteAverage, backdropPath, genreIds, isFavorite)
            }
        }
    }

    override fun getGenres(allGenreUIModels: List<Genre>): List<Genre> {
        TODO("Not yet implemented")
    }

    override fun getGenresText(allGenreUIModels: List<Genre>): String {
        TODO("Not yet implemented")
    }

    override fun getPosterCompleteUrl(): String {
        TODO("Not yet implemented")
    }

    override fun getBackdropCompleteUrl(): String {
        TODO("Not yet implemented")
    }
}