package com.lucasrodrigues.apodnasa.domain.interactor

import androidx.paging.PagingData
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
class ListenToPreviousDaysApodTest {

    @RelaxedMockK
    lateinit var apodRepository: ApodRepository

    lateinit var useCase: ListenToPreviousDaysApods

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = ListenToPreviousDaysApods(apodRepository)
        unmockkAll()
    }

    @Test
    fun `Should call repository`() = runBlockingTest {
        every {
            apodRepository.getApodPaginatedList()
        } returns flowOf(mockk(relaxed = true))

        useCase(Unit).single()

        verify {
            apodRepository.getApodPaginatedList()
        }
    }

    @Test
    fun `Should return repository results`() = runBlockingTest {
        val expectedResults = mockk<PagingData<Apod>>(relaxed = true)

        every {
            apodRepository.getApodPaginatedList()
        } returns flowOf(expectedResults)

        useCase(Unit).collect {
            assertEquals(expectedResults, it)
        }
    }
}