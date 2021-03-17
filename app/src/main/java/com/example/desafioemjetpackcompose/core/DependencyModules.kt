package com.example.desafioemjetpackcompose.core

import android.content.Context
import android.content.SharedPreferences
import com.example.desafioemjetpackcompose.core.api.MovieApi
import com.example.desafioemjetpackcompose.core.api.RestApi
import com.example.desafioemjetpackcompose.data.data_sources.MovieLocalDataSource
import com.example.desafioemjetpackcompose.data.data_sources.MovieLocalDataSourceSharedPrefsImpl
import com.example.desafioemjetpackcompose.data.data_sources.MovieLocalDataSourceSharedPrefsImpl.Companion.SHARED_PREFERENCES_KEY
import com.example.desafioemjetpackcompose.data.data_sources.MovieRemoteDataSource
import com.example.desafioemjetpackcompose.data.data_sources.MovieRemoteDataSourceImpl
import com.example.desafioemjetpackcompose.data.repositories.MovieRepositoryImpl
import com.example.desafioemjetpackcompose.domain.entities.Movie
import com.example.desafioemjetpackcompose.domain.repositories.MovieRepository
import com.example.desafioemjetpackcompose.domain.usecases.*
import com.example.desafioemjetpackcompose.presentation.pages.favorite_movies.FavoriteMoviesViewModel
import com.example.desafioemjetpackcompose.presentation.pages.home.HomeFragmentViewModel
import com.example.desafioemjetpackcompose.presentation.pages.movie_detail.MovieDetailViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object DependencyModules {

    val appModule = module {

        single<MovieApi> { RestApi.getRetrofit().create(MovieApi::class.java) }
        single<SharedPreferences> {
            App.instance.getSharedPreferences(
                SHARED_PREFERENCES_KEY,
                Context.MODE_PRIVATE
            )
        }
        single<Gson> { Gson() }

//        single<MovieLocalDataSource> { MovieLocalDataSourceImpl() }
        single<MovieLocalDataSource> { MovieLocalDataSourceSharedPrefsImpl(get(), get()) }
        single<MovieRemoteDataSource> { MovieRemoteDataSourceImpl(get()) }

        single<MovieRepository> { MovieRepositoryImpl(get(), get()) }

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