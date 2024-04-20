package com.example.desafioemjetpackcompose.movies.network.data_sources

import com.example.desafioemjetpackcompose.movies.network.api.MovieApi
import com.example.desafioemjetpackcompose.movies.domain.exceptions.InvalidApiKeyThrowable
import com.example.desafioemjetpackcompose.movies.domain.exceptions.ResourceNotFoundThrowable
import com.example.desafioemjetpackcompose.movies.domain.data_sources.PageModel
import com.example.desafioemjetpackcompose.movies.domain.data_sources.MovieRemoteDataSource
import com.example.desafioemjetpackcompose.movies.domain.models.Genre
import com.example.desafioemjetpackcompose.movies.domain.models.Movie

class MovieRemoteDataSourceImpl(private val movieApi: MovieApi) : MovieRemoteDataSource {

    override suspend fun getAllMovies(page: Int): List<Movie> {
        //val response = movieApi.getAllMovies(BuildConfig.API_KEY, page)
        val response = movieApi.getAllMovies("9ef244849e3fff41721f707bd23307a9", page)
        if (response.isSuccessful) {
            return response.body()!!.results
        }
        else{
            if(response.code() == 401)
                throw InvalidApiKeyThrowable()
            else if(response.code() == 404)
                throw ResourceNotFoundThrowable()

            throw Throwable()
        }
    }

    override suspend fun getAllMoviesGenres(): List<Genre> {
        val response = movieApi.getAllMoviesGenres("9ef244849e3fff41721f707bd23307a9")
        if (response.isSuccessful) {
            return response.body()!!.genres
        }
        else{
            if(response.code() == 401)
                throw InvalidApiKeyThrowable()
            else if(response.code() == 404)
                throw ResourceNotFoundThrowable()

            throw Throwable()
        }
    }
}