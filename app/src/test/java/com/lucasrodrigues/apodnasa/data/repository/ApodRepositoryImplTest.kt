package com.lucasrodrigues.apodnasa.data.repository

import com.lucasrodrigues.apodnasa.data.local.dao.ApodDao
import com.lucasrodrigues.apodnasa.data.remote.data_source.ApodDataSource
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before

@ExperimentalCoroutinesApi
class ApodRepositoryImplTest {

    @RelaxedMockK
    lateinit var apodDataSource: ApodDataSource

    @RelaxedMockK
    lateinit var apodDao: ApodDao

    lateinit var apodRepositoryImpl: ApodRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        apodRepositoryImpl = ApodRepositoryImpl(apodDataSource, apodDao)
    }
}