package com.lucasrodrigues.apodnasa.ui.home

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.lucasrodrigues.apodnasa.ui.composables.ApodPagedList

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "NASA APOD") })
        }
    ) {
        ApodPagedList(pagedListFlow = viewModel.previousApods)
    }
}