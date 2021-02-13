package com.lucasrodrigues.apodnasa.di

import com.lucasrodrigues.apodnasa.data.remote.ApodAPI
import com.lucasrodrigues.apodnasa.data.remote.data_source.ApodDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RemoteDataSourceModule {

    @Provides
    @Singleton
    fun provideApodDataSource(apodAPI: ApodAPI): ApodDataSource {
        return ApodDataSource(apodAPI)
    }
}