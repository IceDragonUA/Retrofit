package com.evaluation.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestAdapter {

    private final String BASE_URL = "https://api.themoviedb.org/3/";

    private RestApi instance;

    public RestApi getRestApiService() {
        if (instance == null) {
            instance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(provideGson()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(provideOkHttpClient())
                    .build()
                    .create(RestApi.class);
        }
        return instance;
    }

    private static Gson provideGson() {
        return new GsonBuilder().create();
    }

    private static OkHttpClient provideOkHttpClient() {
        final HttpLoggingInterceptor intLogging = new HttpLoggingInterceptor();
        intLogging.level(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient().newBuilder()
                .addInterceptor(intLogging)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }
}
