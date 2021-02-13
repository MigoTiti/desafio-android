package com.lucasrodrigues.apodnasa.extensions

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class DateExtensionsTest {

    @Test
    fun `Yesterday - Should return one day before date`() {
        val date = Calendar.getInstance().run {
            clear()
            set(2021, Calendar.JANUARY, 2)

            time
        }

        val expectedDate = Calendar.getInstance().run {
            clear()

            set(2021, Calendar.JANUARY, 1)

            time
        }

        assertEquals(
            expectedDate,
            date.yesterday()
        )
    }

    @Test
    fun `Plus days - Should add correct days`() {
        val date = Calendar.getInstance().run {
            clear()
            set(2021, Calendar.JANUARY, 2)

            time
        }

        val expectedDate = Calendar.getInstance().run {
            clear()

            set(2021, Calendar.JANUARY, 12)

            time
        }

        assertEquals(
            expectedDate,
            date.plusDays(10)
        )
    }

    @Test
    fun `Minus days - Should subtract correct days`() {
        val date = Calendar.getInstance().run {
            clear()
            set(2021, Calendar.JANUARY, 12)

            time
        }

        val expectedDate = Calendar.getInstance().run {
            clear()

            set(2021, Calendar.JANUARY, 2)

            time
        }

        assertEquals(
            expectedDate,
            date.minusDays(10)
        )
    }

    @Test
    fun `Create date - Should create correct date`() {
        assertEquals(
            Calendar.getInstance().apply {
                clear()
                set(2021, Calendar.MAY, 21)
            }.time,
            createDate(21, Calendar.MAY, 2021)
        )
    }
}