package com.example.desafioemjetpackcompose.movies.domain.usecases

import com.example.desafioemjetpackcompose.movies.domain.models.Movie
import com.example.desafioemjetpackcompose.movies.domain.repositories.MovieRepository

class GetAllMovies(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(page: Int = 1): List<Movie> {
        return movieRepository.getAllMovies(page)
    }
}