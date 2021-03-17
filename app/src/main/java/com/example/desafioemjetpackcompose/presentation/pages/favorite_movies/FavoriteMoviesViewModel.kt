package com.example.desafioemjetpackcompose.presentation.pages.favorite_movies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafioemjetpackcompose.domain.entities.Movie
import com.example.desafioemjetpackcompose.domain.usecases.GetFavoriteMovies
import kotlinx.coroutines.launch

class FavoriteMoviesViewModel(
    private val getFavoriteMoviesUseCase: GetFavoriteMovies,
) : ViewModel() {

    var movies: List<Movie> by mutableStateOf(listOf())
        private set

    fun fetchFavoriteMovies(query: String = "") {
        viewModelScope.launch {
            try {
                val result = getFavoriteMoviesUseCase(query)
                movies = result
            } catch (t: Throwable) {
            }
        }
    }
}