package com.lucasrodrigues.apodnasa.data.remote.data_source

import com.lucasrodrigues.apodnasa.BuildConfig
import com.lucasrodrigues.apodnasa.data.remote.VimeoAPI
import com.lucasrodrigues.apodnasa.domain.model.dto.*
import com.lucasrodrigues.apodnasa.domain.model.mapper.toServerString
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test
import java.util.*

@ExperimentalCoroutinesApi
class VimeoDataSourceTest {

    @RelaxedMockK
    lateinit var vimeoApi: VimeoAPI

    lateinit var vimeoDataSource: VimeoDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        vimeoDataSource = VimeoDataSource(vimeoApi)
        unmockkAll()
    }

    @Test
    fun `Fetch Vimeo video url - should call api`() = runBlockingTest {
        coEvery {
            vimeoApi.fetchVimeoVideoDetails(any())
        } returns mockk(relaxed = true)

        vimeoDataSource.fetchVimeoVideoUrl("")

        coVerify {
            vimeoApi.fetchVimeoVideoDetails(any())
        }
    }

    @Test
    fun `Fetch Vimeo video url - Should pass correct video id`() = runBlockingTest {
        val expectedVideoId = "1234"
        val videoIdSlot = slot<String>()

        coEvery {
            vimeoApi.fetchVimeoVideoDetails(capture(videoIdSlot))
        } returns mockk(relaxed = true)

        vimeoDataSource.fetchVimeoVideoUrl(
            url = "${BuildConfig.VIMEO_VIDEO_FULL_URL}$expectedVideoId",
        )

        assertEquals(expectedVideoId, videoIdSlot.captured)
    }

    @Test
    fun `Fetch Vimeo video url - Should max width progressive file url`() = runBlockingTest {
        val expectedUrl = "www.progressive123"

        coEvery {
            vimeoApi.fetchVimeoVideoDetails(any())
        } returns VimeoVideoDTO(
            request = VimeoVideoRequestDTO(
                files = VimeoVideoRequestFileDTO(
                    progressive = listOf(
                        VimeoVideoProgressiveFileDTO(
                            profile = 1,
                            width = 100,
                            height = 50,
                            url = "www.1234"
                        ),
                        VimeoVideoProgressiveFileDTO(
                            profile = 1,
                            width = 1000,
                            height = 50,
                            url = expectedUrl,
                        ),
                        VimeoVideoProgressiveFileDTO(
                            profile = 1,
                            width = 500,
                            height = 50,
                            url = "www.12345"
                        ),
                    ),
                ),
            ),
        )

        val result = vimeoDataSource.fetchVimeoVideoUrl("")

        assertSame(expectedUrl, result)
    }
}