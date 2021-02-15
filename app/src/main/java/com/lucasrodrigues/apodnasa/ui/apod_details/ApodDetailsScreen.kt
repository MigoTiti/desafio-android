package com.lucasrodrigues.apodnasa.ui.apod_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lucasrodrigues.apodnasa.ui.composable.ApodDetailsMediaContent
import java.text.SimpleDateFormat

@Composable
fun ApodDetailsScreen(viewModel: ApodDetailsViewModel, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detalhes") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onPrimary,
                        )
                    }
                }
            )
        }
    ) {
        val apod by viewModel.apod.observeAsState()

        apod?.let {
            Column {
                ApodDetailsMediaContent(content = it.content)
                Column(
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp,
                            vertical = 16.dp,
                        ),
                ) {
                    Text(
                        text = it.title,
                        modifier = Modifier.padding(bottom = 8.dp),
                        style = MaterialTheme.typography.h5,
                    )
                    Text(
                        text = "Criado em: ${SimpleDateFormat("dd/MM/yyyy").format(it.date)}",
                        modifier = Modifier.padding(bottom = 24.dp),
                        style = MaterialTheme.typography.caption,
                    )
                    Text(text = it.description)
                }
            }
        }
    }
}