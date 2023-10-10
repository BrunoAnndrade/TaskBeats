package com.comunidadedevspace.taskbeats.data.remote

import com.comunidadedevspace.taskbeats.BuildConfig
import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitModule {

    fun createNewsService(): NewsService {

        val apiKey = BuildConfig.API_KEY

        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://api.thenewsapi.com/v1/news/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))

        return retrofit
            .build()
            .create(NewsService::class.java)
    }
}