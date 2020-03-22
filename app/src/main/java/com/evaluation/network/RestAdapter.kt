package com.evaluation.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RestAdapter {

    private val BASE_URL = "https://api.themoviedb.org/3/"

    private var instance: RestApi

    init {
        instance = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(provideOkHttpClient())
            .build()
            .create(RestApi::class.java)
    }

    val restApiService: RestApi
        get() = instance

    private fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val intLogging = HttpLoggingInterceptor()
        intLogging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient().newBuilder()
            .addInterceptor(intLogging)
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }
}