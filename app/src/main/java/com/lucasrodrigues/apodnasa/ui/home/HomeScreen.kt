package com.lucasrodrigues.apodnasa.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.lucasrodrigues.apodnasa.R
import com.lucasrodrigues.apodnasa.domain.model.Failure
import com.lucasrodrigues.apodnasa.extension.navigate
import com.lucasrodrigues.apodnasa.ui.composable.*
import com.lucasrodrigues.apodnasa.ui.routing.Route

@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavController) {
    AppTheme {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) })
            }
        ) {
            val lazyList = viewModel.previousApods.collectAsLazyPagingItems()

            when (LocalConfiguration.current.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    Row {
                        TodayApodItem(
                            apodLiveData = viewModel.todayApod,
                            navController = navController,
                            modifier = Modifier
                                .fillMaxWidth(0.35f)
                                .fillMaxHeight(),
                            maxTitleLines = 1,
                        ) {
                            ApodDetailsMediaContent(
                                content = it.content,
                                imageContentScale = ContentScale.Crop,
                            )
                        }
                        LazyColumn {
                            items(lazyList) { item ->
                                if (item != null)
                                    ApodItem(
                                        apod = item,
                                        modifier = Modifier
                                            .padding(top = 16.dp)
                                            .clickable {
                                                navController.navigate(
                                                    Route.APOD_DETAILS,
                                                    listOf(item.date.time)
                                                )
                                            },
                                    )
                            }

                            lazyList.apply {
                                when {
                                    loadState.refresh is LoadState.Loading -> {
                                        item { LoadingItem(modifier = Modifier.fillParentMaxSize()) }
                                    }
                                    loadState.append is LoadState.Loading -> {
                                        item {
                                            LoadingItem(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 32.dp)
                                            )
                                        }
                                    }
                                    loadState.refresh is LoadState.Error -> {
                                        val e = lazyList.loadState.refresh as LoadState.Error

                                        if (lazyList.itemCount == 0) {
                                            (e.error as? Failure)?.let {
                                                item {
                                                    ErrorItem(
                                                        error = it,
                                                        modifier = Modifier.fillParentMaxSize(),
                                                        textStyle = MaterialTheme.typography.h6,
                                                    ) { retry() }
                                                }
                                            }
                                        }
                                    }
                                    loadState.append is LoadState.Error -> {
                                        val e = lazyList.loadState.append as LoadState.Error

                                        (e.error as? Failure)?.let {
                                            item {
                                                ErrorItem(
                                                    error = it,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(vertical = 32.dp)
                                                ) { retry() }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                else -> {
                    LazyColumn {
                        item {
                            TodayApodItem(
                                apodLiveData = viewModel.todayApod,
                                navController = navController,
                                modifier = Modifier.height(200.dp),
                                maxTitleLines = 2,
                            ) {
                                ApodDetailsMediaContent(
                                    content = it.content,
                                    modifier = Modifier.fillMaxWidth(),
                                    imageContentScale = ContentScale.Crop,
                                )
                            }
                        }

                        items(lazyList) { item ->
                            if (item != null)
                                ApodItem(
                                    apod = item,
                                    modifier = Modifier
                                        .padding(top = 16.dp)
                                        .clickable {
                                            navController.navigate(
                                                Route.APOD_DETAILS,
                                                listOf(item.date.time)
                                            )
                                        },
                                )
                        }

                        lazyList.apply {
                            when {
                                loadState.refresh is LoadState.Loading -> {
                                    item { LoadingItem(modifier = Modifier.fillParentMaxSize()) }
                                }
                                loadState.append is LoadState.Loading -> {
                                    item {
                                        LoadingItem(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 32.dp)
                                        )
                                    }
                                }
                                loadState.refresh is LoadState.Error -> {
                                    val e = lazyList.loadState.refresh as LoadState.Error

                                    if (lazyList.itemCount == 0) {
                                        (e.error as? Failure)?.let {
                                            item {
                                                ErrorItem(
                                                    error = it,
                                                    modifier = Modifier.fillParentMaxSize(),
                                                    textStyle = MaterialTheme.typography.h6,
                                                ) { retry() }
                                            }
                                        }
                                    }
                                }
                                loadState.append is LoadState.Error -> {
                                    val e = lazyList.loadState.append as LoadState.Error

                                    (e.error as? Failure)?.let {
                                        item {
                                            ErrorItem(
                                                error = it,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(vertical = 32.dp)
                                            ) { retry() }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}