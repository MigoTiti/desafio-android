package com.lucasrodrigues.apodnasa.domain.model.mapper

import io.mockk.unmockkAll
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*
import java.util.Calendar.JANUARY

class StringMapperTest {

    @Before
    fun setUp() {
        unmockkAll()
    }

    @Test
    fun `Should map to correct date`() {
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