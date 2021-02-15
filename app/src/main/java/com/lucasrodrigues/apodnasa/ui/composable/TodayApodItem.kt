package com.lucasrodrigues.apodnasa.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.lucasrodrigues.apodnasa.R
import com.lucasrodrigues.apodnasa.domain.model.Apod
import com.lucasrodrigues.apodnasa.extension.navigate
import com.lucasrodrigues.apodnasa.ui.routing.Route

@Composable
fun TodayApodItem(
    apodLiveData: LiveData<Apod?>,
    modifier: Modifier = Modifier,
    showTitle: Boolean,
    navController: NavController,
    content: @Composable ColumnScope.(apod: Apod) -> Unit,
) {
    val apod by apodLiveData.observeAsState()

    apod?.let {
        Column(
            modifier = modifier.clickable {
                navController.navigate(Route.APOD_DETAILS, listOf(it.date.time))
            }
        ) {
            content(it)
            if (showTitle)
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                ) {
                    Text(
                        text = stringResource(R.string.label_today),
                        modifier = Modifier.padding(vertical = 8.dp),
                        style = MaterialTheme.typography.caption,
                    )
                    Text(
                        text = it.title,
                        modifier = Modifier.padding(bottom = 24.dp),
                        style = MaterialTheme.typography.h5,
                        maxLines = 2,
                    )
                }
        }
    }
}