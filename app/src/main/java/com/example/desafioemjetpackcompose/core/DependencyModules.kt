package com.example.desafioemjetpackcompose.core

import android.content.Context
import android.content.SharedPreferences
import com.example.desafioemjetpackcompose.movies.domain.data_sources.MovieLocalDataSource
import com.example.desafioemjetpackcompose.movies.persistence.data_sources.MovieLocalDataSourceSharedPrefsImpl.Companion.SHARED_PREFERENCES_KEY
import com.example.desafioemjetpackcompose.movies.domain.data_sources.MovieRemoteDataSource
import com.example.desafioemjetpackcompose.movies.domain.repositories.MovieRepositoryImpl
import com.example.desafioemjetpackcompose.domain.usecases.*
import com.example.desafioemjetpackcompose.movies.domain.models.Movie
import com.example.desafioemjetpackcompose.presentation.pages.favorite_movies.FavoriteMoviesViewModel
import com.example.desafioemjetpackcompose.presentation.pages.home.HomeFragmentViewModel
import com.example.desafioemjetpackcompose.presentation.pages.movie_detail.MovieDetailViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object DependencyModules {

    val appModule = module {

        single<com.example.desafioemjetpackcompose.movies.network.api.MovieApi> { com.example.desafioemjetpackcompose.movies.network.api.RestApi.getRetrofit().create(
            com.example.desafioemjetpackcompose.movies.network.api.MovieApi::class.java) }
        single<SharedPreferences> {
            App.instance.getSharedPreferences(
                SHARED_PREFERENCES_KEY,
                Context.MODE_PRIVATE
            )
        }
        single<Gson> { Gson() }

//        single<MovieLocalDataSource> { MovieLocalDataSourceImpl() }
        single<MovieLocalDataSource> {
            com.example.desafioemjetpackcompose.movies.persistence.data_sources.MovieLocalDataSourceSharedPrefsImpl(
                get(),
                get()
            )
        }
        single<MovieRemoteDataSource> {
            com.example.desafioemjetpackcompose.movies.network.data_sources.MovieRemoteDataSourceImpl(
                get()
            )
        }

        single<com.example.desafioemjetpackcompose.movies.domain.repositories.MovieRepository> { MovieRepositoryImpl(get(), get()) }

        factory { FavoriteOrDisfavorMovie(get()) }
        factory { GetAllMovies(get()) }
        factory { GetAllMoviesGenres(get()) }
        factory { GetFavoriteMovies(get()) }
        factory { SelectDetailMovie(get()) }
        factory { ViewDetailMovie(get()) }

        viewModel {
            HomeFragmentViewModel(
                favoriteOrDisfavorMovieUseCase = get(),
                selectDetailMovieUseCase = get(),
                getAllMovies = get(),
                getFavoriteMoviesUseCase = get()
            )
        }

        viewModel { (movie: Movie) ->
            MovieDetailViewModel(
                movie = movie,
                getAllMoviesGenres = get(),
                favoriteOrDisfavorMovie = get(),
            )
        }

        viewModel {
            FavoriteMoviesViewModel(
                getFavoriteMoviesUseCase = get()
            )
        }
    }
}