package com.lucasrodrigues.apodnasa.domain.model.mapper

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*
import java.util.Calendar.FEBRUARY

class DateMapperTest {

    @Test
    fun `Should map to correct string`() {
        val expectedString = "2021-02-12"

        assertEquals(
            expectedString,
            Calendar.getInstance().run {
                clear()
                set(2021, FEBRUARY, 12)

                time
            }.toServerString(),
        )
    }
}