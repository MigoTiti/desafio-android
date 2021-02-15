package com.lucasrodrigues.apodnasa.data.remote.data_source

import com.lucasrodrigues.apodnasa.BuildConfig
import com.lucasrodrigues.apodnasa.data.remote.VimeoAPI
import javax.inject.Inject

class VimeoDataSource @Inject constructor(
    private val vimeoAPI: VimeoAPI,
) {

    suspend fun fetchVimeoVideoUrl(url: String): String {
        val newUrl = url.replace(BuildConfig.VIMEO_VIDEO_FULL_URL, "")

        val videoDetails = vimeoAPI.fetchVimeoVideoDetails(newUrl)

        return videoDetails.request.files.progressive.maxByOrNull {
            it.width
        }?.url ?: ""
    }
}