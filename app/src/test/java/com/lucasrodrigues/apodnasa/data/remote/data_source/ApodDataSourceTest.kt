package com.lucasrodrigues.apodnasa.data.remote.data_source

import com.lucasrodrigues.apodnasa.data.remote.ApodAPI
import com.lucasrodrigues.apodnasa.domain.model.dto.ApodDTO
import com.lucasrodrigues.apodnasa.domain.model.mapper.toServerString
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test
import java.util.*

@ExperimentalCoroutinesApi
class ApodDataSourceTest {

    @RelaxedMockK
    lateinit var apodApi: ApodAPI

    lateinit var apodDataSource: ApodDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        apodDataSource = ApodDataSource(apodApi)
        unmockkAll()
    }

    @Test
    fun `Fetch Apod list - should call api`() = runBlockingTest {
        apodDataSource.fetchApodList(Date(), Date())

        coVerify {
            apodApi.fetchApodList(any(), any())
        }
    }

    @Test
    fun `Fetch Apod list - should map parameters`() = runBlockingTest {
        mockkStatic("com.lucasrodrigues.apodnasa.domain.model.mapper.DateMapperKt")

        val expectedStartDate = "2020-01-10"
        val expectedEndDate = "2020-01-20"

        val startDate = Date(12L)
        val endDate = Date(13L)

        every {
            startDate.toServerString()
        } returns expectedStartDate

        every {
            endDate.toServerString()
        } returns expectedEndDate

        val startDateSlot = slot<String>()
        val endDateSlot = slot<String>()

        coEvery {
            apodApi.fetchApodList(
                startDate = capture(startDateSlot),
                endDate = capture(endDateSlot),
            )
        } returns mockk()

        apodDataSource.fetchApodList(
            startDate,
            endDate
        )

        assertSame(expectedStartDate, startDateSlot.captured)
        assertSame(expectedEndDate, endDateSlot.captured)
    }

    @Test
    fun `Fetch Apod list - should return service results`() = runBlockingTest {
        mockkStatic("com.lucasrodrigues.apodnasa.domain.model.mapper.DateMapperKt")

        val expectedResult = mockk<List<ApodDTO>>(relaxed = true)

        coEvery {
            apodApi.fetchApodList(any(), any())
        } returns expectedResult

        val result = apodDataSource.fetchApodList(
            mockk(relaxed = true),
            mockk(relaxed = true),
        )

        assertSame(expectedResult, result)
    }
}