package com.example.desafioemjetpackcompose.domain.usecases

import com.example.desafioemjetpackcompose.domain.entities.Movie
import com.example.desafioemjetpackcompose.domain.repositories.MovieRepository

class FavoriteOrDisfavorMovie(private val repository: MovieRepository) {

    suspend operator fun invoke(movie: Movie): Movie =
        repository.favoriteOrDisfavorMovie(movie)

}