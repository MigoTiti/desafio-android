package com.lucasrodrigues.apodnasa.data.repository

import com.lucasrodrigues.apodnasa.data.local.dao.ApodDao
import com.lucasrodrigues.apodnasa.data.remote.data_source.ApodDataSource
import com.lucasrodrigues.apodnasa.domain.model.dbo.ApodDBO
import com.lucasrodrigues.apodnasa.domain.repository.ApodRepository
import java.util.*
import javax.inject.Inject

class ApodRepositoryImpl @Inject constructor(
    private val apodDataSource: ApodDataSource,
    private val apodDao: ApodDao,
) : ApodRepository {
    override suspend fun getApodPage(initialDate: Date, pageSize: Int): List<ApodDBO> {
        TODO("Not yet implemented")
    }
}