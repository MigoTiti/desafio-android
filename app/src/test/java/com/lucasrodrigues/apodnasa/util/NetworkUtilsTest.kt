package com.lucasrodrigues.apodnasa.util

import com.lucasrodrigues.apodnasa.domain.model.Failure
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection.HTTP_INTERNAL_ERROR
import java.net.HttpURLConnection.HTTP_UNAVAILABLE
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class NetworkUtilsTest {

    @Before
    fun setUp() {
        unmockkAll()
    }

    @Test
    fun `Request API - Should return value when success`() = runBlockingTest {
        val expectedValue = mockk<Any>(relaxed = true)

        assertEquals(
            expectedValue,
            requestApi {
                expectedValue
            }
        )
    }

    @Test
    fun `Request API - Should throw NetworkException when IOException`() {
        assertThrows(Failure.NetworkConnection::class.java) {
            runBlockingTest {
                requestApi {
                    throw IOException()
                }
            }
        }
    }

    @Test
    fun `Request API - Should throw NetworkException when UnknownHostException`() {
        assertThrows(Failure.NetworkConnection::class.java) {
            runBlockingTest {
                requestApi {
                    throw UnknownHostException()
                }
            }
        }
    }

    @Test
    fun `Request API - Should throw ServerError when HTTP_UNAVAILABLE`() {
        assertThrows(Failure.ServerError::class.java) {
            runBlockingTest {
                requestApi {
                    throw HttpException(
                        Response.error<Any>(
                            HTTP_UNAVAILABLE,
                            "".toResponseBody()
                        ),
                    )
                }
            }
        }
    }

    @Test
    fun `Request API - Should throw ServerError when HTTP_INTERNAL_ERROR`() {
        assertThrows(Failure.ServerError::class.java) {
            runBlockingTest {
                requestApi {
                    throw HttpException(
                        Response.error<Any>(
                            HTTP_INTERNAL_ERROR,
                            "".toResponseBody()
                        ),
                    )
                }
            }
        }
    }

    @Test
    fun `Request API - Should throw UnknownError when other exception`() {
        assertThrows(Failure.UnknownError::class.java) {
            runBlockingTest {
                requestApi {
                    throw Exception()
                }
            }
        }
    }
}