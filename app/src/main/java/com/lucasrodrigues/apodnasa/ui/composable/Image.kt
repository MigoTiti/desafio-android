package com.lucasrodrigues.apodnasa.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.imageloading.ImageLoadState
import dev.chrisbanes.accompanist.imageloading.MaterialLoadingImage

@Composable
fun Image(
    url: String,
    contentScale: ContentScale,
    backgroundColor: Color = Color.LightGray,
) {
    CoilImage(data = url) { imageState ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
        ) {
            when (imageState) {
                is ImageLoadState.Success -> {
                    MaterialLoadingImage(
                        painter = imageState.painter,
                        contentDescription = null,
                        fadeInEnabled = true,
                        fadeInDurationMs = 600,
                        contentScale = contentScale,
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