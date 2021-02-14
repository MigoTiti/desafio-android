package com.lucasrodrigues.apodnasa.di

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.lucasrodrigues.apodnasa.ui.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityComponent::class)
object MainActivityModule {

    @Provides
    fun provideNavController(@ActivityContext activity: Context): NavController {
        return (activity as MainActivity).binding.navHostFragment.findNavController()
    }
}