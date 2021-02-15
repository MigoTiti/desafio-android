package com.lucasrodrigues.apodnasa.data.repository

import androidx.paging.*
import com.lucasrodrigues.apodnasa.data.local.dao.ApodDao
import com.lucasrodrigues.apodnasa.data.remote.data_source.ApodDataSource
import com.lucasrodrigues.apodnasa.domain.model.Apod
import com.lucasrodrigues.apodnasa.domain.model.dbo.ApodDBO
import com.lucasrodrigues.apodnasa.domain.model.mapper.toApod
import com.lucasrodrigues.apodnasa.domain.model.mapper.toApodDBO
import com.lucasrodrigues.apodnasa.domain.repository.ApodRepository
import com.lucasrodrigues.apodnasa.extensions.createDate
import com.lucasrodrigues.apodnasa.extensions.minusDays
import com.lucasrodrigues.apodnasa.components.ApodRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class ApodRepositoryImpl @Inject constructor(
    private val apodDataSource: ApodDataSource,
    private val apodDao: ApodDao,
) : ApodRepository {
    override suspend fun getApodPage(referenceDate: Date, pageSize: Int): List<ApodDBO> {
        try {
            val pageStart = referenceDate.minusDays(1)
            val pageEnd = pageStart.minusDays(pageSize)

            val apodList = apodDataSource.fetchApodList(
                startDate = maxOf(pageEnd, createDate(16, Calendar.JUNE, 1995)),
                endDate = minOf(pageStart, Date()),
            ).map {
                it.toApodDBO()
            }

            apodDao.insertList(apodList)

            return apodList
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }

    override suspend fun getFirstItem(): Apod? {
        return apodDao.getFirstItem()?.toApod()
    }

    override suspend fun getLastItem(): Apod? {
        return apodDao.getLastItem()?.toApod()
    }

    override fun getTodayApod(): Flow<Apod> {
        return apodDao
            .listenToMostRecentApod()
            .map {
                it.toApod()
            }
    }

    override fun getApod(timestamp: Long): Flow<Apod> {
        return apodDao
            .listenToApod(timestamp)
            .map {
                it.toApod()
            }
    }

    @ExperimentalPagingApi
    override fun getApodPaginatedList(): Flow<PagingData<Apod>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = ApodRemoteMediator(
                apodRepository = this,
            ),
        ) {
            apodDao.apodPagingSource()
        }.flow
            .map {
                it.map { apodDBO ->
                    apodDBO.toApod()
                }
            }
    }
}