package com.lucasrodrigues.apodnasa.data.remote

import com.lucasrodrigues.apodnasa.BuildConfig
import com.lucasrodrigues.apodnasa.domain.model.dto.ApodDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ApodAPI {

    @GET("/planetary/apod/")
    suspend fun fetchApodList(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = BuildConfig.APOD_API_KEY,
    ): List<ApodDTO>
}