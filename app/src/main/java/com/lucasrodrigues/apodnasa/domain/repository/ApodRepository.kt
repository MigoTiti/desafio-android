package com.lucasrodrigues.apodnasa.domain.repository

import com.lucasrodrigues.apodnasa.domain.model.dbo.ApodDBO
import java.util.*

interface ApodRepository {

    suspend fun getApodPage(referenceDate: Date, pageSize: Int): List<ApodDBO>
}