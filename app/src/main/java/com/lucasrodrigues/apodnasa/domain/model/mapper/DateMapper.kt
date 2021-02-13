package com.lucasrodrigues.apodnasa.domain.model.mapper

import java.text.SimpleDateFormat
import java.util.*

val sdf = SimpleDateFormat("yyyy-MM-dd").apply {
    timeZone = TimeZone.getTimeZone("UTC")
}

@Synchronized
fun Date.toServerString(): String {
    return sdf.format(this)
}