package com.lucasrodrigues.apodnasa.domain.repository

import com.lucasrodrigues.apodnasa.domain.model.dbo.ApodDBO
import java.util.*

interface ApodRepository {

    suspend fun getApodPage(initialDate: Date, pageSize: Int): List<ApodDBO>
}