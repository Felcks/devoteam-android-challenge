package com.example.desafioemjetpackcompose.movies.domain.data_sources

data class PageModel<T>(
    val page: Int,
    val results: List<T>,
    val total_results: Int,
    val total_pages: Int
)