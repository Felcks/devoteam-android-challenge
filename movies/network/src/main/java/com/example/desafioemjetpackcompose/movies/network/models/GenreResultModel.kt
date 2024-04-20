package com.example.desafioemjetpackcompose.movies.network.models

import com.example.desafioemjetpackcompose.movies.domain.models.Genre

data class GenreResultModel(
    val genres: List<Genre>
)