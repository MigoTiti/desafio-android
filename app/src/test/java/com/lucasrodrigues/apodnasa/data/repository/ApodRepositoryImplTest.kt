package com.lucasrodrigues.apodnasa.data.repository

import com.lucasrodrigues.apodnasa.data.local.dao.ApodDao
import com.lucasrodrigues.apodnasa.data.remote.data_source.ApodDataSource
import com.lucasrodrigues.apodnasa.domain.model.Apod
import com.lucasrodrigues.apodnasa.domain.model.dbo.ApodDBO
import com.lucasrodrigues.apodnasa.domain.model.dto.ApodDTO
import com.lucasrodrigues.apodnasa.domain.model.mapper.toApod
import com.lucasrodrigues.apodnasa.domain.model.mapper.toApodDBO
import com.lucasrodrigues.apodnasa.domain.model.mapper.toServerString
import com.lucasrodrigues.apodnasa.extensions.createDate
import com.lucasrodrigues.apodnasa.extensions.minusDays
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test
import java.util.*

@ExperimentalCoroutinesApi
class ApodRepositoryImplTest {

    @RelaxedMockK
    lateinit var apodDataSource: ApodDataSource

    @RelaxedMockK
    lateinit var apodDao: ApodDao

    private lateinit var apodRepositoryImpl: ApodRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        apodRepositoryImpl = ApodRepositoryImpl(apodDataSource, apodDao)
        unmockkAll()
    }

    @Test
    fun `Get Apod - Should call dao`() = runBlockingTest {
        mockkStatic("com.lucasrodrigues.apodnasa.domain.model.mapper.ApodMapperKt")

        val expectedApodDBO = mockk<ApodDBO>(relaxed = true)

        every {
            apodDao.listenToApod(any())
        } returns flowOf(expectedApodDBO)

        every {
            expectedApodDBO.toApod()
        } returns mockk(relaxed = true)

        apodRepositoryImpl.getApod(0L).single()

        verify {
            apodDao.listenToApod(any())
        }
    }

    @Test
    fun `Get Apod - Should pass parameters to dao`() = runBlockingTest {
        mockkStatic("com.lucasrodrigues.apodnasa.domain.model.mapper.ApodMapperKt")

        val expectedTimestamp = 0L
        val timestampSlot = slot<Long>()

        every {
            apodDao.listenToApod(capture(timestampSlot))
        } returns flowOf(mockk(relaxed = true))

        every {
            any<ApodDBO>().toApod()
        } returns mockk(relaxed = true)

        apodRepositoryImpl.getApod(expectedTimestamp).single()

        assertEquals(
            expectedTimestamp,
            timestampSlot.captured,
        )
    }

    @Test
    fun `Get Apod - Should map dbo`() = runBlockingTest {
        mockkStatic("com.lucasrodrigues.apodnasa.domain.model.mapper.ApodMapperKt")

        val expectedApodDBO = mockk<ApodDBO>(relaxed = true)
        val expectedApod = mockk<Apod>(relaxed = true)

        every {
            apodDao.listenToApod(any())
        } returns flowOf(expectedApodDBO)

        every {
            expectedApodDBO.toApod()
        } returns expectedApod

        apodRepositoryImpl.getApod(0L).collect {
            assertEquals(expectedApod, it)
        }
    }

    @Test
    fun `Get today Apod - Should call dao`() = runBlockingTest {
        mockkStatic("com.lucasrodrigues.apodnasa.domain.model.mapper.ApodMapperKt")

        every {
            apodDao.listenToMostRecentApod()
        } returns flowOf(mockk(relaxed = true))

        every {
            any<ApodDBO>().toApod()
        } returns mockk(relaxed = true)

        apodRepositoryImpl.getTodayApod().single()

        verify {
            apodDao.listenToMostRecentApod()
        }
    }

    @Test
    fun `Get today Apod - Should map dbo`() = runBlockingTest {
        mockkStatic("com.lucasrodrigues.apodnasa.domain.model.mapper.ApodMapperKt")

        val expectedApodDBO = mockk<ApodDBO>(relaxed = true)
        val expectedApod = mockk<Apod>(relaxed = true)

        every {
            apodDao.listenToMostRecentApod()
        } returns flowOf(expectedApodDBO)

        every {
            expectedApodDBO.toApod()
        } returns expectedApod

        apodRepositoryImpl.getTodayApod().collect {
            assertEquals(expectedApod, it)
        }
    }

    @Test
    fun `Get Apod page - Should call DataSource`() = runBlockingTest {
        coEvery {
            apodDataSource.fetchApodList(any(), any())
        } returns listOf()

        apodRepositoryImpl.getApodPage(Date(), 1)

        coVerify {
            apodDataSource.fetchApodList(any(), any())
        }
    }

    @Test
    fun `Get Apod page - Start date should be the reference date plus (page size + 1)`() =
        runBlockingTest {
            val pageSize = 20

            val referenceDate = createDate(10, Calendar.JANUARY, 2021)
            val expectedStartDate = referenceDate.minusDays(pageSize + 1)

            val startDateSlot = slot<Date>()

            coEvery {
                apodDataSource.fetchApodList(
                    startDate = capture(startDateSlot),
                    endDate = any(),
                )
            } returns listOf()

            apodRepositoryImpl.getApodPage(referenceDate, pageSize)

            assertEquals(
                expectedStartDate,
                startDateSlot.captured,
            )
        }

    @Test
    fun `Get Apod page - Start date should be the max between page end and (16-06-1995)`() =
        runBlockingTest {
            val pageSize = 20

            val referenceDate = createDate(20, Calendar.JUNE, 1995)
            val expectedStartDate = createDate(16, Calendar.JUNE, 1995)

            val startDateSlot = slot<Date>()

            coEvery {
                apodDataSource.fetchApodList(
                    startDate = capture(startDateSlot),
                    endDate = any(),
                )
            } returns listOf()

            apodRepositoryImpl.getApodPage(referenceDate, pageSize)

            assertEquals(
                expectedStartDate,
                startDateSlot.captured,
            )
        }

    @Test
    fun `Get Apod page - End date should be reference date minus one day`() =
        runBlockingTest {
            val referenceDate = createDate(10, Calendar.JANUARY, 2021)
            val expectedEndDate = referenceDate.minusDays(1)

            val endDateSlot = slot<Date>()

            coEvery {
                apodDataSource.fetchApodList(
                    startDate = any(),
                    endDate = capture(endDateSlot),
                )
            } returns listOf()

            apodRepositoryImpl.getApodPage(referenceDate, 20)

            assertEquals(
                expectedEndDate,
                endDateSlot.captured,
            )
        }

    @Test
    fun `Get Apod page - End date should be current date when page start is greater`() =
        runBlockingTest {
            val referenceDate = createDate(20, Calendar.FEBRUARY, 2021)
            val expectedEndDate = Date()

            val endDateSlot = slot<Date>()

            coEvery {
                apodDataSource.fetchApodList(
                    startDate = any(),
                    endDate = capture(endDateSlot),
                )
            } returns listOf()

            apodRepositoryImpl.getApodPage(referenceDate, 20)

            assertEquals(
                expectedEndDate.toServerString(),
                endDateSlot.captured.toServerString(),
            )
        }

    @Test
    fun `Get Apod page - Should call mapping function in DTO to DBO`() = runBlockingTest {
        mockkStatic("com.lucasrodrigues.apodnasa.domain.model.mapper.ApodMapperKt")

        val dto = mockk<ApodDTO>(relaxed = true)
        val expectedDbo = mockk<ApodDBO>(relaxed = true)

        coEvery {
            apodDataSource.fetchApodList(any(), any())
        } returns listOf(dto)

        every {
            dto.toApodDBO()
        } returns expectedDbo

        val result = apodRepositoryImpl.getApodPage(Date(), 1)

        assertSame(
            expectedDbo,
            result[0],
        )
    }

    @Test
    fun `Get Apod page - Should insert result list in database`() = runBlockingTest {
        mockkStatic("com.lucasrodrigues.apodnasa.domain.model.mapper.ApodMapperKt")

        val dto = mockk<ApodDTO>(relaxed = true)
        val expectedDbo = mockk<ApodDBO>(relaxed = true)

        val listInsertedInDb = slot<List<ApodDBO>>()

        coEvery {
            apodDataSource.fetchApodList(any(), any())
        } returns listOf(dto)

        every {
            dto.toApodDBO()
        } returns expectedDbo

        coEvery {
            apodDao.insertList(capture(listInsertedInDb))
        } just runs

        apodRepositoryImpl.getApodPage(Date(), 1)

        assertEquals(
            listOf(expectedDbo),
            listInsertedInDb.captured,
        )
    }

    @Test
    fun `Get Apod page - Should return mapped list`() = runBlockingTest {
        mockkStatic("com.lucasrodrigues.apodnasa.domain.model.mapper.ApodMapperKt")

        val dto = mockk<ApodDTO>(relaxed = true)
        val expectedDbo = mockk<ApodDBO>(relaxed = true)

        coEvery {
            apodDataSource.fetchApodList(any(), any())
        } returns listOf(dto)

        every {
            dto.toApodDBO()
        } returns expectedDbo

        assertEquals(
            listOf(expectedDbo),
            apodRepositoryImpl.getApodPage(Date(), 1),
        )
    }
}