package com.example.desafioemjetpackcompose.domain.usecases

import com.example.desafioemjetpackcompose.domain.entities.Movie
import com.example.desafioemjetpackcompose.domain.repositories.MovieRepository

class ViewDetailMovie(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(): Movie {
        return movieRepository.getCachedDetailMovie()
    }
}