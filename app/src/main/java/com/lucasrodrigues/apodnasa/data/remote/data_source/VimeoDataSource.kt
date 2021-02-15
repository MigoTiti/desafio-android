package com.lucasrodrigues.apodnasa.data.remote.data_source

import com.lucasrodrigues.apodnasa.data.remote.VimeoAPI
import javax.inject.Inject

class VimeoDataSource @Inject constructor(
    private val vimeoAPI: VimeoAPI,
) {

    suspend fun fetchVimeoVideoUrl(url: String): String {
        val newUrl = url.replace("https://player.vimeo.com/video/", "")

        val videoDetails = vimeoAPI.fetchVimeoVideoDetails(newUrl)

        return videoDetails.request.files.progressive.maxByOrNull {
            it.width
        }!!.url
    }
}