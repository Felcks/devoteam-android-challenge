package com.example.desafioemjetpackcompose.movies.domain.usecases

import com.example.desafioemjetpackcompose.movies.domain.models.Movie
import com.example.desafioemjetpackcompose.movies.domain.repositories.MovieRepository

class SelectDetailMovie(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(movie: Movie) {
        return movieRepository.cacheDetailMovie(movie)
    }
}