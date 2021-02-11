package com.lucasrodrigues.apodnasa.domain.model.dto

data class ApodDTO(
    val date: String,
    val title: String,
    val explanation: String,
    val hdurl: String? = null,
    val url: String? = null,
    val media_type: String,
    val thumbnail_url: String? = null,
    val serviceVersion: String,
)