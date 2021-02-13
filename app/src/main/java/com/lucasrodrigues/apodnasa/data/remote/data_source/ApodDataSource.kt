package com.lucasrodrigues.apodnasa.data.remote.data_source

import com.lucasrodrigues.apodnasa.data.remote.ApodAPI
import com.lucasrodrigues.apodnasa.domain.model.dto.ApodDTO
import com.lucasrodrigues.apodnasa.domain.model.mapper.toServerString
import java.util.*
import javax.inject.Inject

class ApodDataSource @Inject constructor(
    private val apodAPI: ApodAPI,
) {

    suspend fun fetchApodList(startDate: Date, endDate: Date): List<ApodDTO> {
        return apodAPI.fetchApodList(
            startDate = startDate.toServerString(),
            endDate = endDate.toServerString(),
        )
    }
}