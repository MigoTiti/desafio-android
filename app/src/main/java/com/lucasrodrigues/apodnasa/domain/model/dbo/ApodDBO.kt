package com.lucasrodrigues.apodnasa.domain.model.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "apods"
)
data class ApodDBO(
    @PrimaryKey val timestamp: Long,
    val title: String,
    val description: String,
    val mediaType: String,
    val contentUrl: String? = null,
    val imageHdUrl: String? = null,
    val videoThumbnailUrl: String? = null,
)