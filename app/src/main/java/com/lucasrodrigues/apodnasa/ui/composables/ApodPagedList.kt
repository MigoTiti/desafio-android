package com.lucasrodrigues.apodnasa.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.lucasrodrigues.apodnasa.domain.model.Apod
import com.lucasrodrigues.apodnasa.extensions.navigate
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
    }
}