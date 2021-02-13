package com.lucasrodrigues.apodnasa.data.repository

import com.lucasrodrigues.apodnasa.data.local.dao.ApodDao
import com.lucasrodrigues.apodnasa.data.remote.data_source.ApodDataSource
import com.lucasrodrigues.apodnasa.domain.model.dbo.ApodDBO
import com.lucasrodrigues.apodnasa.domain.model.mapper.toApodDBO
import com.lucasrodrigues.apodnasa.domain.repository.ApodRepository
import com.lucasrodrigues.apodnasa.extensions.createDate
import com.lucasrodrigues.apodnasa.extensions.minusDays
import java.util.*
import javax.inject.Inject

class ApodRepositoryImpl @Inject constructor(
    private val apodDataSource: ApodDataSource,
    private val apodDao: ApodDao,
) : ApodRepository {
    override suspend fun getApodPage(referenceDate: Date, pageSize: Int): List<ApodDBO> {
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
    }
}