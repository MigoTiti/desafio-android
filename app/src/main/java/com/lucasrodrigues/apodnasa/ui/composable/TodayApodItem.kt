package com.lucasrodrigues.apodnasa.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.lucasrodrigues.apodnasa.domain.model.Apod
import com.lucasrodrigues.apodnasa.extension.navigate
import com.lucasrodrigues.apodnasa.ui.routing.Route

@Composable
fun TodayApodItem(apodLiveData: LiveData<Apod>, navController: NavController) {
    val apod by apodLiveData.observeAsState()

    apod?.let {
        Column(
            modifier = Modifier.clickable {
                navController.navigate(Route.APOD_DETAILS, listOf(it.date.time))
            }
        ) {
            ApodDetailsMediaContent(it.content, ContentScale.Crop)
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
            ) {
                Text(
                    text = "Hoje",
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = MaterialTheme.typography.caption,
                )
                Text(
                    text = it.title,
                    modifier = Modifier.padding(bottom = 24.dp),
                    style = MaterialTheme.typography.h5,
                )
            }
        }
    }
}