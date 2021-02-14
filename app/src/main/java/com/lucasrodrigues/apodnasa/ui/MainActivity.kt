package com.lucasrodrigues.apodnasa.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.lucasrodrigues.apodnasa.ui.apod_details.ApodDetailsScreen
import com.lucasrodrigues.apodnasa.ui.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") {
                    HomeScreen(
                        viewModel = viewModel(
                            factory = HiltViewModelFactory(LocalContext.current, it)
                        ),
                        navController = navController,
                    )
                }
                composable(
                    route = "apod_details/{apodTimestamp}",
                    arguments = listOf(
                        navArgument("apodTimestamp") {
                            type = NavType.LongType
                        }
                    )
                ) {
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