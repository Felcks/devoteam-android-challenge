package com.example.desafioemjetpackcompose.domain.usecases

import com.example.desafioemjetpackcompose.movies.domain.models.Movie
import com.example.desafioemjetpackcompose.movies.ui.models.MovieUIModel
import com.example.desafioemjetpackcompose.movies.domain.repositories.MovieRepository

class ViewDetailMovie(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(): Movie {
        return movieRepository.getCachedDetailMovie()
    }
}