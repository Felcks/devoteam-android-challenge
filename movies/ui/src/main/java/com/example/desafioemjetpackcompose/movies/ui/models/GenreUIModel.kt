package com.example.desafioemjetpackcompose.movies.ui.models

import com.example.desafioemjetpackcompose.movies.domain.models.Genre

data class GenreUIModel(
    override val id: Int,
    override val name: String,
) : Genre