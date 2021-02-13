package com.lucasrodrigues.apodnasa.di

import com.lucasrodrigues.apodnasa.BuildConfig
import com.lucasrodrigues.apodnasa.data.remote.ApodAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RemoteDataModule {

    @Provides
    @Singleton
    fun provideApodAPI(retrofit: Retrofit): ApodAPI {
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.APOD_BASE_URL)
            .client(
                OkHttpClient
                    .Builder()
                    .callTimeout(60L, TimeUnit.SECONDS)
                    .connectTimeout(60L, TimeUnit.SECONDS)
                    .readTimeout(60L, TimeUnit.SECONDS)
                    .writeTimeout(60L, TimeUnit.SECONDS)
                    .apply {
                        if (BuildConfig.DEBUG) {
                            addInterceptor(
                                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
                                    level = HttpLoggingInterceptor.Level.BODY
                                }
                            )
                        }
                    }
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}