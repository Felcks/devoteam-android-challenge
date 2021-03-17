package com.example.desafioemjetpackcompose.data.models

import com.example.desafioemjetpackcompose.domain.entities.Genre

data class GenreModel(
    val id: Int,
    val name: String?
){

    fun toEntity(): Genre{
        return Genre(
            this.id,
            this.name ?: ""
        )
    }
}