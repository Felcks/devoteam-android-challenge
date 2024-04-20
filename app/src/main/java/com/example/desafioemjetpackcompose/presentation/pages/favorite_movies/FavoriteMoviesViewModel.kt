package com.example.desafioemjetpackcompose.presentation.pages.favorite_movies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafioemjetpackcompose.movies.ui.models.MovieUIModel
import com.example.desafioemjetpackcompose.domain.usecases.GetFavoriteMovies
import kotlinx.coroutines.launch

class FavoriteMoviesViewModel(
    private val getFavoriteMoviesUseCase: GetFavoriteMovies,
) : ViewModel() {

    var movies: List<MovieUIModel> by mutableStateOf(listOf())
        private set

    fun fetchFavoriteMovies(query: String = "") {
        viewModelScope.launch {
            try {
                val result = getFavoriteMoviesUseCase(query)
                movies = result.map { MovieUIModel.fromDomain(it) }
            } catch (t: Throwable) {
            }
        }
    }
}