package com.prasetyanurangga.githubuserwithkoin.data.di.module

import com.google.gson.Gson
import com.prasetyanurangga.githubuserwithkoin.data.service.ApiService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit



    val base_url = "https://api.github.com/"

    fun providesRetrofit(
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder().baseUrl(base_url)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }


    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    fun providesOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)


        return client.build()
    }


    fun providesGson(): Gson {
        return Gson()
    }

    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    val appModule = module {
        single { providesOkHttpClient() }
        single { providesRetrofit(get()) }
        single { provideApiService(get()) }
        single { providesGson() }
        single { providesGsonConverterFactory() }
    }



