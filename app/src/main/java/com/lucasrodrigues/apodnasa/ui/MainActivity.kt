package com.lucasrodrigues.apodnasa.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.lucasrodrigues.apodnasa.extensions.routeComposable
import com.lucasrodrigues.apodnasa.ui.apod_details.ApodDetailsScreen
import com.lucasrodrigues.apodnasa.ui.home.HomeScreen
import com.lucasrodrigues.apodnasa.ui.routing.Route
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Route.HOME.fullName
            ) {
                routeComposable(Route.HOME) {
                    HomeScreen(
                        viewModel = viewModel(
                            factory = HiltViewModelFactory(LocalContext.current, it)
                        ),
                        navController = navController,
                    )
                }
                routeComposable(Route.APOD_DETAILS) {
                    ApodDetailsScreen(
                        viewModel = viewModel(
                            factory = HiltViewModelFactory(LocalContext.current, it)
                        ),
                    )
                }
            }
        }
    }
}