package com.example.desafioemjetpackcompose.presentation.pages.movie_detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.desafioemjetpackcompose.movies.ui.models.GenreUIModel
import com.example.desafioemjetpackcompose.movies.ui.models.MovieUIModel
import com.example.desafioemjetpackcompose.domain.usecases.FavoriteOrDisfavorMovie
import com.example.desafioemjetpackcompose.domain.usecases.GetAllMoviesGenres
import com.example.desafioemjetpackcompose.movies.domain.models.Genre
import com.example.desafioemjetpackcompose.movies.domain.models.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieDetailViewModel(
    movie: Movie,
    private val getAllMoviesGenres: GetAllMoviesGenres,
    private val favoriteOrDisfavorMovie: FavoriteOrDisfavorMovie
) : ViewModel() {

    var mMovie: Movie by mutableStateOf(movie)

    var genres: List<Genre> by mutableStateOf(listOf())
        private set

    init {
        fetchAllGenres()
    }

    private fun fetchAllGenres() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = getAllMoviesGenres()
                genres = result
            } catch (t: Throwable) {
            }
        }
    }

    fun favorOrDisfavorMovie() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                //val result = favoriteOrDisfavorMovie(mMovie.copy())
                val result = favoriteOrDisfavorMovie(mMovie)
                mMovie = result
            } catch (e: Exception) {

            }
        }
    }
}