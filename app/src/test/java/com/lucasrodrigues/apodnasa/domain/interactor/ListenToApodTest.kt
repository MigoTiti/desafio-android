package com.lucasrodrigues.apodnasa.domain.interactor

import com.lucasrodrigues.apodnasa.domain.model.Apod
import com.lucasrodrigues.apodnasa.domain.repository.ApodRepository
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ListenToApodTest {

    @RelaxedMockK
    lateinit var apodRepository: ApodRepository

    lateinit var useCase: ListenToApod

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = ListenToApod(apodRepository)
        unmockkAll()
    }

    @Test
    fun `Should call repository`() = runBlockingTest {
        every {
            apodRepository.getApod(any())
        } returns flowOf(mockk(relaxed = true))

        useCase(mockk(relaxed = true)).single()

        verify {
            apodRepository.getApod(any())
        }
    }

    @Test
    fun `Should pass parameters to repository`() = runBlockingTest {
        val expectedParams = mockk<ListenToApod.Params>(relaxed = true)
        val paramsSlot = slot<Long>()

        every {
            apodRepository.getApod(capture(paramsSlot))
        } returns flowOf(mockk(relaxed = true))

        useCase(expectedParams).single()

        assertEquals(
            expectedParams.timestamp,
            paramsSlot.captured
        )
    }

    @Test
    fun `Should return repository results`() = runBlockingTest {
        val expectedResults = mockk<Apod>(relaxed = true)

        every {
            apodRepository.getApod(any())
        } returns flowOf(expectedResults)

        useCase(mockk(relaxed = true)).collect {
            assertEquals(expectedResults, it)
        }
    }
}