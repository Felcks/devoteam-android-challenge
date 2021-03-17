package com.example.desafioemjetpackcompose.domain.usecases

import com.example.desafioemjetpackcompose.domain.entities.Movie
import com.example.desafioemjetpackcompose.domain.repositories.MovieRepository

class GetFavoriteMovies(private val repository: MovieRepository) {

    suspend operator fun invoke(filter: String = ""): List<Movie> {
        return repository.getFavoriteMovies(filter)
    }
}