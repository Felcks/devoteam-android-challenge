package com.example.desafioemjetpackcompose.presentation.pages.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafioemjetpackcompose.domain.entities.Movie
import com.example.desafioemjetpackcompose.domain.usecases.FavoriteOrDisfavorMovie
import com.example.desafioemjetpackcompose.domain.usecases.GetAllMovies
import com.example.desafioemjetpackcompose.domain.usecases.GetFavoriteMovies
import com.example.desafioemjetpackcompose.domain.usecases.SelectDetailMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragmentViewModel(
    private val favoriteOrDisfavorMovieUseCase: FavoriteOrDisfavorMovie,
    private val selectDetailMovieUseCase: SelectDetailMovie,
    private val getAllMovies: GetAllMovies,
    private val getFavoriteMoviesUseCase: GetFavoriteMovies
) : ViewModel() {

    val pageSize = 20
    var currentPage by mutableStateOf(1)
    var loading by mutableStateOf(false)
    private var movieListScrollPosition = 0

    var realMovies: List<Movie> by mutableStateOf(listOf())
        private set

    init {
        fetchAllMovies()
    }

    private fun fetchAllMovies() {
        viewModelScope.launch {
            try {
                val result = getAllMovies(currentPage)
                appendNewMovies(result)
            } catch (t: Throwable) {
            }
        }
    }

    fun nextPage() {
        viewModelScope.launch {
            if ((movieListScrollPosition + 1) >= (currentPage * pageSize)) {
                loading = true
                incrementPageNumber()

                if (currentPage > 1) {
                    val result = getAllMovies(currentPage)
                    appendNewMovies(result)
                }
                loading = false
            }
        }
    }

    fun favorOrDisfavorMovie(movie: Movie) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = favoriteOrDisfavorMovieUseCase(movie.copy())
            val pos = realMovies.indexOf(movie)
            realMovies = realMovies.toMutableList().also {
                it[pos] = result
            }
        }
    }

    fun updateMoviesFavoriteStatus() {
        if (realMovies.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val favoriteMovies = getFavoriteMoviesUseCase()
                    val favoriteMoviesIDs = favoriteMovies.map { it.id }
                    realMovies.forEach {
                        it.isFavorite = favoriteMoviesIDs.contains(it.id)
                    }
                } catch (t: Throwable) {
                }
            }
        }
    }

    private fun appendNewMovies(movies: List<Movie>) {
        realMovies = realMovies + movies
    }

    private fun incrementPageNumber() {
        currentPage += 1
    }

    fun onChangeMovieScrollPosition(position: Int) {
        movieListScrollPosition = position
    }
}