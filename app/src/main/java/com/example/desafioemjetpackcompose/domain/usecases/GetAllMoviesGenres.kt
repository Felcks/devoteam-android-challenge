package com.example.desafioemjetpackcompose.domain.usecases

import com.example.desafioemjetpackcompose.domain.entities.Genre
import com.example.desafioemjetpackcompose.domain.repositories.MovieRepository

class GetAllMoviesGenres(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(): List<Genre> {
        return movieRepository.getAllMoviesGenres()
    }
}