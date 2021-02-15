package com.lucasrodrigues.apodnasa.ui.composable

import android.net.Uri
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.lucasrodrigues.apodnasa.R
import com.lucasrodrigues.apodnasa.domain.model.MediaContent
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun ApodDetailsMediaContent(
    content: MediaContent,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(16f / 12f),
    imageContentScale: ContentScale,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        when (content) {
            is MediaContent.Image -> ApodDetailsImageContent(
                image = content,
                contentScale = imageContentScale,
            )
            is MediaContent.Video -> ApodDetailsVideoContent(video = content)
            MediaContent.None -> Text(
                text = stringResource(R.string.label_no_available_resource),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h5,
            )
        }
    }
}

@Composable
fun ApodDetailsImageContent(image: MediaContent.Image, contentScale: ContentScale) {
    Image(image.hdUrl, contentScale, Color.Black)
}

@Composable
fun ApodDetailsVideoContent(video: MediaContent.Video) {
    if (video.url.startsWith("https://www.youtube.com/"))
        YouTubeVideo(url = video.url)
    else
        ExoPlayerVideo(url = video.url)
}

@Composable
fun YouTubeVideo(url: String) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    AndroidView(
        viewBlock = {
            YouTubePlayerView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
            }
        },
    ) { youTubePlayerView ->
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.apply {
            getPlayerUiController()
                .showFullscreenButton(false)
                .showMenuButton(false)

            getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
                override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                    val videoId = url.split("/").last().split("?").first()

                    youTubePlayer.cueVideo(videoId, 0f)
                }
            })
        }
    }
}

@Composable
fun ExoPlayerVideo(url: String) {
    val context = LocalContext.current

    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build().apply {
            this.prepare(
                ProgressiveMediaSource.Factory(
                    DefaultDataSourceFactory(
                        context,
                        Util.getUserAgent(context, context.packageName)
                    )
                )
                    .createMediaSource(Uri.parse(url))
            )
        }
    }

    AndroidView(
        viewBlock = {
            PlayerView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
            }
        },
    ) { exoPlayerView ->
        exoPlayerView.useController = true
        exoPlayerView.player = exoPlayer
        exoPlayer.playWhenReady = false
    }
}