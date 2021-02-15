package com.lucasrodrigues.apodnasa.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.lucasrodrigues.apodnasa.domain.model.Apod
import com.lucasrodrigues.apodnasa.domain.model.MediaContent
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.imageloading.ImageLoadState
import dev.chrisbanes.accompanist.imageloading.MaterialLoadingImage
import java.text.SimpleDateFormat

@Composable
fun ApodItem(apod: Apod, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .padding(start = 16.dp)
                .width(150.dp)
                .aspectRatio(16f / 12f)
        ) {
            when (apod.content) {
                is MediaContent.Image -> ApodImageContent(content = apod.content)
                is MediaContent.Video -> ApodVideoContent(content = apod.content)
            }
        }
        Column(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp,
                )
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = apod.title,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(bottom = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = "Criado em: ${SimpleDateFormat("dd/MM/yyyy").format(apod.date)}",
                style = MaterialTheme.typography.caption,
            )
        }
    }
}

@Composable
fun ApodVideoContent(content: MediaContent.Video) {
    ApodThumbnail(thumbnailUrl = content.thumbnailUrl)
}

@Composable
fun ApodImageContent(content: MediaContent.Image) {
    ApodThumbnail(thumbnailUrl = content.thumbnailUrl)
}

@Composable
fun ApodThumbnail(thumbnailUrl: String) {
    CoilImage(
        data = thumbnailUrl) { imageState ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(5.dp))
                .background(Color.LightGray),
        ) {
            when (imageState) {
                is ImageLoadState.Success -> {
                    MaterialLoadingImage(
                        painter = imageState.painter,
                        contentDescription = null,
                        fadeInEnabled = true,
                        fadeInDurationMs = 600,
                        contentScale = ContentScale.Crop,
                    )
                }
                is ImageLoadState.Error -> {
                }
                ImageLoadState.Loading -> {
                    CircularProgressIndicator()
                }
                ImageLoadState.Empty -> {
                }
            }
        }
    }
}