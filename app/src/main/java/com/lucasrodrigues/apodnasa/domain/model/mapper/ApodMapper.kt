package com.lucasrodrigues.apodnasa.domain.model.mapper

import com.lucasrodrigues.apodnasa.domain.model.Apod
import com.lucasrodrigues.apodnasa.domain.model.MediaContent
import com.lucasrodrigues.apodnasa.domain.model.dbo.ApodDBO
import com.lucasrodrigues.apodnasa.domain.model.dto.ApodDTO
import java.util.*

fun ApodDTO.toApod(): Apod {
    return Apod(
        date = date.toDate(),
        title = title,
        description = explanation,
        content = when (media_type) {
            "image" -> MediaContent.Image(
                thumbnailUrl = url!!,
                hdUrl = hdurl ?: url,
            )
            "video" -> MediaContent.Video(
                thumbnailUrl = thumbnail_url!!,
                url = url!!,
            )
            else -> MediaContent.None
        }
    )
}

fun ApodDTO.toApodDBO(): ApodDBO {
    return ApodDBO(
        timestamp = date.toDate().time,
        title = title,
        description = explanation,
        mediaType = media_type,
        imageHdUrl = hdurl,
        contentUrl = url,
        videoThumbnailUrl = thumbnail_url,
    )
}

fun ApodDBO.toApod(): Apod {
    return Apod(
        date = Date(timestamp),
        title = title,
        description = description,
        content = when (mediaType) {
            "image" -> MediaContent.Image(
                thumbnailUrl = contentUrl!!,
                hdUrl = imageHdUrl!!,
            )
            "video" -> MediaContent.Video(
                thumbnailUrl = videoThumbnailUrl!!,
                url = contentUrl!!,
            )
            else -> MediaContent.None
        }
    )
}