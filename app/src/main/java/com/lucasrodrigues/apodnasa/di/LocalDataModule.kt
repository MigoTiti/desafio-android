package com.lucasrodrigues.apodnasa.di

import android.content.Context
import com.lucasrodrigues.apodnasa.data.local.LocalDatabase
import com.lucasrodrigues.apodnasa.data.local.dao.ApodDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

    @Provides
    @Singleton
    fun providesApodDao(localDatabase: LocalDatabase): ApodDao {
        return localDatabase.apodDao()
    }

    @Provides
    @Singleton
    fun providesLocalDatabase(@ApplicationContext context: Context): LocalDatabase {
        return LocalDatabase.getInstance(context.applicationContext)
    }
}