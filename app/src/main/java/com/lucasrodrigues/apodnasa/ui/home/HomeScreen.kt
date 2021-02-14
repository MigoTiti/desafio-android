package com.lucasrodrigues.apodnasa.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.lucasrodrigues.apodnasa.ui.composables.ApodPagedList

@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "NASA APOD") })
        }
    ) {
        Column {
            ApodPagedList(
                pagedListFlow = viewModel.previousApods,
                navController = navController,
            )
        }
    }
}