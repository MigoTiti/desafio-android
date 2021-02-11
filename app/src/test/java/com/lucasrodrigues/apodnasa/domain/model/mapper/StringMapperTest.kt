package com.lucasrodrigues.apodnasa.domain.model.mapper

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*
import java.util.Calendar.JANUARY

class StringMapperTest {

    @Test
    fun `Should map correct date`() {
        val expectedDate = Calendar.getInstance().run {
            clear()
            set(2021, JANUARY, 12)

            time
        }

        assertEquals(
            expectedDate,
            "2021-01-12".toDate(),
        )
    }
}