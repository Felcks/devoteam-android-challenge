package com.example.desafioemjetpackcompose.domain.usecases

import com.example.desafioemjetpackcompose.data.repositories.MovieRepositoryImpl
import com.example.desafioemjetpackcompose.domain.entities.Movie
import com.example.desafioemjetpackcompose.domain.repositories.MovieRepository

class SelectDetailMovie(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(movie: Movie) {
        return movieRepository.cacheDetailMovie(movie)
    }
}