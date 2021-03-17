package com.example.desafioemjetpackcompose.domain.usecases

import com.example.desafioemjetpackcompose.domain.entities.Movie
import com.example.desafioemjetpackcompose.domain.repositories.MovieRepository

class GetAllMovies(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(page: Int = 1): List<Movie> {
        return movieRepository.getAllMovies(page)
    }
}