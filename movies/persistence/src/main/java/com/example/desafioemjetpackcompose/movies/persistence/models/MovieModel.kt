package com.example.desafioemjetpackcompose.movies.persistence.models

import com.example.desafioemjetpackcompose.movies.domain.models.Genre
import com.example.desafioemjetpackcompose.movies.domain.models.Movie
import java.text.SimpleDateFormat
import java.util.Date

data class MovieModel(
    val poster_path: String,
    val adult: Boolean,
    override val overview: String,
    val release_date: String?,
    val genre_ids: List<Int>,
    override val id: Int,
    val original_title: String,
    val original_language: String,
    override val title: String,
    val backdrop_path: String?,
    override val popularity: Double,
    val vote_count: Int,
    override val video: Boolean,
    val vote_average: Double,
    val is_favorite: Boolean
) : Movie {

    override val originalTitle: String
        get() = original_title
    override val originalLanguage: String
        get() = original_language
    override val posterPath: String
        get() = poster_path
    override val isAdult: Boolean
        get() = adult
    override val releaseDate: Date
        get() = if (!release_date.isNullOrEmpty()) SimpleDateFormat("yyyy-MM-dd").parse(release_date)!! else Date()
    override val voteCount: Int
        get() = vote_count
    override val voteAverage: Double
        get() = vote_average
    override val backdropPath: String
        get() = backdrop_path ?: ""
    override var genreIds: List<Int> = listOf()
        get() = genre_ids
        set(value) { field = value }
    override var isFavorite: Boolean = false
        get() = is_favorite
        set(value) { field = value }

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

    fun toEntity(): MovieModel { return this
        /*return Movie(
            posterPath = poster_path,
            isAdult = adult,
            overview = overview,
            releaseDate = if (release_date != null && release_date.isNotEmpty()) SimpleDateFormat("yyyy-MM-dd").parse(
                release_date
            ) else Date(),
            genreIds = genre_ids,
            id = id,
            originalTitle = original_title,
            originalLanguage = original_language,
            title = title,
            popularity = popularity,
            voteCount = vote_count,
            video = video,
            backdropPath = backdrop_path ?: "",
            voteAverage = vote_average,
            isFavorite = is_favorite
        ) */
    }

    companion object {
        fun fromEntity(entity: Movie): MovieModel {
            with(entity) {
                return MovieModel(
                    id = id,
                    original_title = originalTitle,
                    original_language = originalLanguage,
                    title = title,
                    poster_path = posterPath,
                    adult = isAdult,
                    overview = overview,
                    release_date = SimpleDateFormat("yyyy-MM-dd").format(releaseDate),
                    popularity = popularity,
                    vote_count = voteCount,
                    video = video,
                    vote_average = voteAverage,
                    genre_ids = genreIds,
                    backdrop_path = backdropPath,
                    is_favorite = isFavorite
                )
            }
        }
    }
}