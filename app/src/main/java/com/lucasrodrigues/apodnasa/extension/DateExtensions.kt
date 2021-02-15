package com.lucasrodrigues.apodnasa.extension

import java.util.*

fun createDate(day: Int, month: Int, year: Int): Date {
    return Calendar.getInstance().run {
        clear()
        set(year, month, day)
        time
    }
}

fun Date.yesterday(): Date {
    return Calendar.getInstance().run {
        time = this@yesterday

        roll(Calendar.DAY_OF_MONTH, false)

        time
    }
}

fun Date.plusDays(days: Int): Date {
    return Calendar.getInstance().run {
        time = this@plusDays

        add(Calendar.DAY_OF_MONTH, days)

        time
    }
}

fun Date.minusDays(days: Int): Date {
    return Calendar.getInstance().run {
        time = this@minusDays

        add(Calendar.DAY_OF_MONTH, -days)

        time
    }
}