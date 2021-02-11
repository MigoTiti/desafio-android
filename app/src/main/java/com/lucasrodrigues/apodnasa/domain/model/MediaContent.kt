package com.lucasrodrigues.apodnasa.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class MediaContent : Parcelable {

    @Parcelize
    data class Image(
        val thumbnailUrl: String,
        val hdUrl: String,
    ) : MediaContent()

    @Parcelize
    data class Video(
        val thumbnailUrl: String,
        val url: String,
    ) : MediaContent()

    @Parcelize
    object None : MediaContent()
}