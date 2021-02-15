package com.lucasrodrigues.apodnasa.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.lucasrodrigues.apodnasa.domain.model.Apod
import com.lucasrodrigues.apodnasa.domain.model.Failure
import com.lucasrodrigues.apodnasa.extension.navigate
import com.lucasrodrigues.apodnasa.ui.routing.Route
import kotlinx.coroutines.flow.Flow

@Composable
fun ApodPagedList(
    pagedListFlow: Flow<PagingData<Apod>>,
    navController: NavController,
) {
    val lazyList = pagedListFlow.collectAsLazyPagingItems()

    LazyColumn {
        items(lazyList) { item ->
            ApodItem(
                apod = item!!,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable {
                        navController.navigate(Route.APOD_DETAILS, listOf(item.date.time))
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