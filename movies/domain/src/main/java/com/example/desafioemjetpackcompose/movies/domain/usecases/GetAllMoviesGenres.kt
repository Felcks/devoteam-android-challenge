package com.example.desafioemjetpackcompose.movies.domain.usecases

import com.example.desafioemjetpackcompose.movies.domain.models.Genre
import com.example.desafioemjetpackcompose.movies.domain.repositories.MovieRepository

class GetAllMoviesGenres(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(): List<Genre> {
        return movieRepository.getAllMoviesGenres()
    }
}