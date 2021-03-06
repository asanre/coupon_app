package com.example.couponapp.di

import com.example.couponapp.BuildConfig
import com.example.couponapp.common.data.adapters.CustomResponseAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val koinNetworkModule = module {

    single {
        OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    single {
        Retrofit
            .Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(get())
            .addCallAdapterFactory(CustomResponseAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}