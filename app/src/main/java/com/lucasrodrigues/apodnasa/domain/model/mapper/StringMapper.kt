package com.lucasrodrigues.apodnasa.domain.model.mapper

import java.util.*

fun String.toDate(): Date {
    return Calendar.getInstance().run {
        val dateRaw = this@toDate.split("-")

        clear()
        set(
            dateRaw[0].toInt(),
            dateRaw[1].toInt() - 1,
            dateRaw[2].toInt(),
        )

        time
    }
}