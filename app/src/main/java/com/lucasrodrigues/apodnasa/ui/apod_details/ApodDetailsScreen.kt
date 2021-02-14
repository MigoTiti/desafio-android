package com.lucasrodrigues.apodnasa.ui.apod_details

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun ApodDetailsScreen(viewModel: ApodDetailsViewModel) {
    val apod by viewModel.apod.observeAsState()

    Text(text = apod?.title ?: "")
}