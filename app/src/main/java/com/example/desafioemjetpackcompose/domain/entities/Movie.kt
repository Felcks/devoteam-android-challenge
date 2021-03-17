package com.example.desafioemjetpackcompose.domain.entities

import android.os.Parcelable
import com.example.desafioemjetpackcompose.BuildConfig
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Movie(
    val id: Int,
    val originalTitle: String,
    val originalLanguage: String,
    val title: String,
    val posterPath: String,
    val isAdult: Boolean,
    val overview: String,
    val releaseDate: Date,
    val popularity: Double,
    val voteCount: Int,
    val video: Boolean,
    val voteAverage: Double,
    val backdropPath: String,
    var genreIds: List<Int>,
    var isFavorite: Boolean = false
) : Parcelable {

    fun getGenres(allGenres: List<Genre>): List<Genre> {
        return allGenres.filter {
            genreIds.contains(it.id)
        }
    }

    fun getGenresText(allGenres: List<Genre>): String {
        return getGenres(allGenres).map { it.name }.toString().replace("]", "").replace("[", "")
    }

    fun getPosterCompleteUrl() = "${BuildConfig.IMAGE_URL}${posterPath}"
    fun getBackdropCompleteUrl() = "${BuildConfig.IMAGE_URL}${backdropPath}"
}

val movies = listOf(
    Movie(
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
        genreIds = listOf(1, 2)
    ),
)