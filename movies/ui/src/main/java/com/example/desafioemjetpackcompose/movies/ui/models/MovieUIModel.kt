package com.example.desafioemjetpackcompose.movies.ui.models

import android.os.Parcelable
import com.example.desafioemjetpackcompose.movies.domain.models.Genre
import com.example.desafioemjetpackcompose.movies.domain.models.Movie
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class MovieUIModel(
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
    override var isFavorite: Boolean = false
) : Parcelable, Movie {

    override fun getGenres(allGenreUIModels: List<Genre>): List<Genre> {
        return allGenreUIModels.filter {
            genreIds.contains(it.id)
        }
    }

    override fun getGenresText(allGenreUIModels: List<Genre>): String {
        return getGenres(allGenreUIModels).map { it.name }.toString().replace("]", "").replace("[", "")
    }

    override fun getPosterCompleteUrl() = "https://image.tmdb.org/t/p/w500${posterPath}" //"${BuildConfig.IMAGE_URL}${posterPath}"
    override fun getBackdropCompleteUrl() = "https://image.tmdb.org/t/p/w500${backdropPath}" //${BuildConfig.IMAGE_URL}${backdropPath}"

    companion object {
        fun fromDomain(movie: Movie) : MovieUIModel {
            return with(movie) {
                MovieUIModel(
                    id = id,
                    originalTitle = originalTitle,
                    originalLanguage = originalLanguage,
                    title = title,
                    posterPath = posterPath,
                    isAdult = isAdult,
                    overview = overview,
                    releaseDate = releaseDate,
                    popularity = popularity,
                    voteCount = voteCount,
                    video = video,
                    voteAverage = voteAverage,
                    backdropPath = backdropPath,
                    genreIds = genreIds
                )
            }
        }
    }
}

val movieUIModels = listOf(
    MovieUIModel(
        id = 0,
        originalTitle = "O retorno dos mortos vivos",
        originalLanguage = "Portugues",
        title = "The return of the living dead",
        posterPath = "/lPsD10PP4rgUGiGR4CCXA6iY0QQ.jpg",
        isAdult = false,
        overview = "Descrição",
        releaseDate = Date(),
        popularity = 1.0,
        voteCount = 100,
        video = false,
        voteAverage = 1.5,
        backdropPath = "/hJuDvwzS0SPlsE6MNFOpznQltDZ.jpg",
        genreIds = listOf(1, 2),
        isFavorite = false
    ),
    MovieUIModel(
        id = 0,
        originalTitle = "O retorno dos mortos vivos",
        originalLanguage = "Portugues",
        title = "The return of the living dead",
        posterPath = "/lPsD10PP4rgUGiGR4CCXA6iY0QQ.jpg",
        isAdult = false,
        overview = "Descrição",
        releaseDate = Date(),
        popularity = 1.0,
        voteCount = 100,
        video = false,
        voteAverage = 1.5,
        backdropPath = "/hJuDvwzS0SPlsE6MNFOpznQltDZ.jpg",
        genreIds = listOf(1, 2),
        isFavorite = true,
    ),
)