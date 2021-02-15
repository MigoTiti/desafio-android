package com.lucasrodrigues.apodnasa.ui.apod_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.lucasrodrigues.apodnasa.R
import com.lucasrodrigues.apodnasa.ui.composable.ApodDetailsMediaContent
import com.lucasrodrigues.apodnasa.ui.composable.AppTheme
import java.text.SimpleDateFormat

@Composable
fun ApodDetailsScreen(viewModel: ApodDetailsViewModel, navController: NavController) {
    AppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.label_details)) },
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
                LazyColumn {
                    item {
                        ApodDetailsMediaContent(
                            content = it.content,
                            imageContentScale = ContentScale.Inside,
                        )
                    }
                    item {
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
                                text = stringResource(
                                    R.string.label_created_at,
                                    SimpleDateFormat("dd/MM/yyyy").format(it.date),
                                ),
                                modifier = Modifier.padding(bottom = 24.dp),
                                style = MaterialTheme.typography.caption,
                            )
                            Text(text = it.description)
                        }
                    }
                }
            }
        }
    }
}