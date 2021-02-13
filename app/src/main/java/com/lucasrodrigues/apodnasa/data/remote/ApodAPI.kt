package com.lucasrodrigues.apodnasa.data.remote

import com.lucasrodrigues.apodnasa.BuildConfig
import com.lucasrodrigues.apodnasa.domain.model.dto.ApodDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ApodAPI {

    @GET
    suspend fun fetchApodList(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("thumbs") showThumbs: String = "True",
        @Query("api_key") apiKey: String = BuildConfig.APOD_API_KEY,
    ): List<ApodDTO>
}