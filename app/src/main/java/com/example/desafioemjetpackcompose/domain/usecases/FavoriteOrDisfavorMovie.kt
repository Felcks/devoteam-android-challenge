package com.example.desafioemjetpackcompose.domain.usecases

import com.example.desafioemjetpackcompose.movies.domain.models.Movie
import com.example.desafioemjetpackcompose.movies.ui.models.MovieUIModel
import com.example.desafioemjetpackcompose.movies.domain.repositories.MovieRepository

class FavoriteOrDisfavorMovie(private val repository: MovieRepository) {

    suspend operator fun invoke(movie: Movie): Movie =
        repository.favoriteOrDisfavorMovie(movie)

}