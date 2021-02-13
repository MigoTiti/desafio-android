package com.lucasrodrigues.apodnasa.domain.model.mapper

import com.lucasrodrigues.apodnasa.domain.model.MediaContent
import com.lucasrodrigues.apodnasa.domain.model.dbo.ApodDBO
import com.lucasrodrigues.apodnasa.domain.model.dto.ApodDTO
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class ApodMapperTest {

    @Test
    fun `ApodDTO to Apod - Should map common data correctly`() {
        mockkStatic("com.lucasrodrigues.apodnasa.domain.model.mapper.StringMapperKt")

        val expectedDate = mockk<Date>(relaxed = true)

        every { any<String>().toDate() } returns expectedDate

        ApodDTO(
            date = "",
            title = "teste",
            explanation = "teste2",
            media_type = "",
            serviceVersion = "",
        ).toApod().apply {
            assertEquals(expectedDate, this.date)
            assertEquals("teste", this.title)
            assertEquals("teste2", this.description)
        }
    }

    @Test
    fun `ApodDTO to Apod - Should map image content correctly`() {
        mockkStatic("com.lucasrodrigues.apodnasa.domain.model.mapper.StringMapperKt")

        every { any<String>().toDate() } returns mockk(relaxed = true)

        val expectedContent = MediaContent.Image(
            thumbnailUrl = "teste.com",
            hdUrl = "teste2.com",
        )

        assertEquals(
            expectedContent,
            ApodDTO(
                date = "",
                title = "",
                explanation = "",
                media_type = "image",
                hdurl = "teste2.com",
                url = "teste.com",
                serviceVersion = "",
            ).toApod().content
        )
    }

    @Test
    fun `ApodDTO to Apod - Should map video content correctly`() {
        mockkStatic("com.lucasrodrigues.apodnasa.domain.model.mapper.StringMapperKt")

        every { any<String>().toDate() } returns mockk(relaxed = true)

        val expectedContent = MediaContent.Video(
            thumbnailUrl = "teste.com",
            url = "teste2.com",
        )

        assertEquals(
            expectedContent,
            ApodDTO(
                date = "",
                title = "",
                explanation = "",
                media_type = "video",
                thumbnail_url = "teste.com",
                url = "teste2.com",
                serviceVersion = "",
            ).toApod().content
        )
    }

    @Test
    fun `ApodDTO to Apod - Should map none content correctly`() {
        mockkStatic("com.lucasrodrigues.apodnasa.domain.model.mapper.StringMapperKt")

        every { any<String>().toDate() } returns mockk(relaxed = true)

        val expectedContent = MediaContent.None

        assertEquals(
            expectedContent,
            ApodDTO(
                date = "",
                title = "",
                explanation = "",
                media_type = "other",
                serviceVersion = "",
            ).toApod().content
        )
    }

    @Test
    fun `ApodDTO to ApodDBO - Should map correctly`() {
        ApodDTO(
            date = "2020-01-20",
            title = "teste",
            explanation = "teste2",
            media_type = "mediaTeste",
            hdurl = "teste.com",
            url = "teste2.com",
            thumbnail_url = "teste3.com",
            serviceVersion = "",
        ).toApodDBO().apply {
            assertEquals("2020-01-20".toDate().time, this.timestamp)
            assertEquals("teste", this.title)
            assertEquals("teste2", this.description)
            assertEquals("mediaTeste", this.mediaType)
            assertEquals("teste.com", this.imageHdUrl)
            assertEquals("teste2.com", this.contentUrl)
            assertEquals("teste3.com", this.videoThumbnailUrl)
        }
    }

    @Test
    fun `ApodDBO to Apod - Should map common data correctly`() {
        ApodDBO(
            timestamp = 1L,
            title = "teste",
            description = "teste2",
            mediaType = "",
        ).toApod().apply {
            assertEquals(Date(1L), this.date)
            assertEquals("teste", this.title)
            assertEquals("teste2", this.description)
        }
    }

    @Test
    fun `ApodDBO to Apod - Should map image content correctly`() {
        val expectedContent = MediaContent.Image(
            thumbnailUrl = "teste.com",
            hdUrl = "teste2.com",
        )

        assertEquals(
            expectedContent,
            ApodDBO(
                timestamp = 0L,
                title = "",
                description = "",
                mediaType = "image",
                imageHdUrl = "teste2.com",
                contentUrl = "teste.com",
            ).toApod().content
        )
    }

    @Test
    fun `ApodDBO to Apod - Should map video content correctly`() {
        val expectedContent = MediaContent.Video(
            thumbnailUrl = "teste.com",
            url = "teste2.com",
        )

        assertEquals(
            expectedContent,
            ApodDBO(
                timestamp = 0L,
                title = "",
                description = "",
                mediaType = "video",
                videoThumbnailUrl = "teste.com",
                contentUrl = "teste2.com",
            ).toApod().content
        )
    }

    @Test
    fun `ApodDBO to Apod - Should map none content correctly`() {
        val expectedContent = MediaContent.None

        assertEquals(
            expectedContent,
            ApodDBO(
                timestamp = 0L,
                title = "",
                description = "",
                mediaType = "other",
            ).toApod().content
        )
    }
}