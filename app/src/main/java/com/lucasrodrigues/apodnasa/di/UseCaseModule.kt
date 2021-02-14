package com.lucasrodrigues.apodnasa.di

import com.lucasrodrigues.apodnasa.domain.interactor.ListenToApod
import com.lucasrodrigues.apodnasa.domain.interactor.ListenToPreviousDaysApods
import com.lucasrodrigues.apodnasa.domain.interactor.ListenToTodayApod
import com.lucasrodrigues.apodnasa.domain.repository.ApodRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideListenToTodayApod(apodRepository: ApodRepository): ListenToTodayApod {
        return ListenToTodayApod(apodRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideListenToPreviousDaysApods(apodRepository: ApodRepository): ListenToPreviousDaysApods {
        return ListenToPreviousDaysApods(apodRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideListenToApod(apodRepository: ApodRepository): ListenToApod {
        return ListenToApod(apodRepository)
    }
}