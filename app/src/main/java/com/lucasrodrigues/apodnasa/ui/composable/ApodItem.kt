package com.lucasrodrigues.apodnasa.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VideoLibrary
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
import java.text.SimpleDateFormat

@Composable
fun ApodItem(apod: Apod, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .padding(start = 16.dp)
                .width(150.dp)
                .aspectRatio(16f / 12f)
                .clip(shape = RoundedCornerShape(5.dp))
        ) {
            when (apod.content) {
                is MediaContent.Image -> ApodItemImageContent(content = apod.content)
                is MediaContent.Video -> ApodItemVideoContent(content = apod.content)
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
fun ApodItemVideoContent(content: MediaContent.Video) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        ApodThumbnail(thumbnailUrl = content.thumbnailUrl)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black.copy(alpha = 0.5f)),
        ) {
            Icon(
                imageVector = Icons.Filled.VideoLibrary,
                contentDescription = null,
                tint = MaterialTheme.colors.onPrimary,
            )
        }
    }
}

@Composable
fun ApodItemImageContent(content: MediaContent.Image) {
    ApodThumbnail(thumbnailUrl = content.thumbnailUrl)
}

@Composable
fun ApodThumbnail(thumbnailUrl: String) {
    Image(thumbnailUrl, ContentScale.Crop)
}