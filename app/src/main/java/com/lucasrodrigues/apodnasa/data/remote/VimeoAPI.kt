package com.lucasrodrigues.apodnasa.data.remote

import com.lucasrodrigues.apodnasa.domain.model.dto.VimeoVideoDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface VimeoAPI {

    @GET("/video/{videoId}/config")
    suspend fun fetchVimeoVideoDetails(
        @Path("videoId") videoId: String,
    ): VimeoVideoDTO
}