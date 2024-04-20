package com.example.desafioemjetpackcompose.movies.domain.usecases

import com.example.desafioemjetpackcompose.movies.domain.models.Movie
import com.example.desafioemjetpackcompose.movies.domain.repositories.MovieRepository

class GetFavoriteMovies(private val repository: MovieRepository) {

    suspend operator fun invoke(filter: String = ""): List<Movie> {
        return repository.getFavoriteMovies(filter)
    }
}