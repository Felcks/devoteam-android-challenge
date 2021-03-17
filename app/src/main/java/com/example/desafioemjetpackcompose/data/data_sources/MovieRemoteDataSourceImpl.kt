package com.example.desafioemjetpackcompose.data.data_sources

import android.util.Log
import com.example.desafioemjetpackcompose.BuildConfig
import com.example.desafioemjetpackcompose.core.api.MovieApi
import com.example.desafioemjetpackcompose.core.exceptions.InvalidApiKeyThrowable
import com.example.desafioemjetpackcompose.core.exceptions.ResourceNotFoundThrowable
import com.example.desafioemjetpackcompose.data.models.GenreResultModel
import com.example.desafioemjetpackcompose.data.models.MovieModel
import com.example.desafioemjetpackcompose.data.models.PageModel
import java.lang.Exception

class MovieRemoteDataSourceImpl(private val movieApi: MovieApi) : MovieRemoteDataSource {

    override suspend fun getAllMovies(page: Int): PageModel<MovieModel> {
        val response = movieApi.getAllMovies(BuildConfig.API_KEY, page)
        if (response.isSuccessful) {
            return response.body()!!
        }
        else{
            if(response.code() == 401)
                throw InvalidApiKeyThrowable()
            else if(response.code() == 404)
                throw ResourceNotFoundThrowable()

            throw Throwable()
        }
    }

    override suspend fun getAllMoviesGenres(): GenreResultModel {
        val response = movieApi.getAllMoviesGenres(BuildConfig.API_KEY)
        if (response.isSuccessful) {
            return response.body()!!
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