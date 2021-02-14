package com.lucasrodrigues.apodnasa.di

import com.lucasrodrigues.apodnasa.data.repository.ApodRepositoryImpl
import com.lucasrodrigues.apodnasa.domain.repository.ApodRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun providesApodRepository(
        apodRepositoryImpl: ApodRepositoryImpl
    ): ApodRepository
}