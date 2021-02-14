package com.lucasrodrigues.apodnasa.domain.repository

import androidx.paging.PagingData
import com.lucasrodrigues.apodnasa.domain.model.Apod
import com.lucasrodrigues.apodnasa.domain.model.dbo.ApodDBO
import kotlinx.coroutines.flow.Flow
import java.util.*

interface ApodRepository {

    suspend fun getApodPage(referenceDate: Date, pageSize: Int): List<ApodDBO>

    fun getApodPaginatedList(): Flow<PagingData<Apod>>
    fun getTodayApod(): Flow<Apod>
    fun getApod(timestamp: Long): Flow<Apod>
}