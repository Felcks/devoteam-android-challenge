package com.example.desafioemjetpackcompose.core.api

import android.content.Context
import android.net.ConnectivityManager
import com.example.desafioemjetpackcompose.core.App
import com.example.desafioemjetpackcompose.core.exceptions.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


object RestApi {

    private val retrofit: Retrofit
    fun getRetrofit() = retrofit

    init {
        val clientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(NetworkConnectionInterceptor(App.instance))


        val client = clientBuilder.build()
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .client(client)
            .build()
    }
}

