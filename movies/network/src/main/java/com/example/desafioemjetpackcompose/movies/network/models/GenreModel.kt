package com.example.desafioemjetpackcompose.movies.network.models

import com.example.desafioemjetpackcompose.movies.domain.models.Genre

data class GenreModel(
    override val id: Int,
    override val name: String
) : Genre {

    fun toEntity(): Genre {
        return object : Genre {
            override val id: Int
                get() = this@GenreModel.id
            override val name: String
                get() = this@GenreModel.name

        }
    }
}