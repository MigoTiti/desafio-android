package com.lucasrodrigues.apodnasa.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Apod(
    val date: Date,
    val title: String,
    val description: String,
    val content: MediaContent,
) : Parcelable