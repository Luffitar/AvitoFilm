package com.example.avitofilm.di

import com.example.avitofilm.data.network.FilmsApi
import com.example.avitofilm.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class DataModule{

    @Provides
    fun provideClient(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return Retrofit.Builder()
            .baseUrl(BASE_URL).client(
                OkHttpClient()
                .newBuilder()
                .addInterceptor(loggingInterceptor)
                .build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideGifApi(retrofit: Retrofit): FilmsApi {
        return retrofit.create(FilmsApi::class.java)
    }

}