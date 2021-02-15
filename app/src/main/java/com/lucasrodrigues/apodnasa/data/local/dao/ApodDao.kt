package com.lucasrodrigues.apodnasa.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.lucasrodrigues.apodnasa.domain.model.dbo.ApodDBO
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface ApodDao : BaseDao<ApodDBO> {

    @Query("SELECT * FROM apods WHERE timestamp != :currentDate ORDER BY timestamp DESC")
    fun apodPagingSource(
        currentDate: Long = Calendar.getInstance().run {
            val currentCalendar = Calendar.getInstance()

            clear()
            set(Calendar.YEAR, currentCalendar.get(Calendar.YEAR))
            set(Calendar.MONTH, currentCalendar.get(Calendar.MONTH))
            set(Calendar.DAY_OF_MONTH, currentCalendar.get(Calendar.DAY_OF_MONTH))

            time.time
        }
    ): PagingSource<Int, ApodDBO>

    @Query("SELECT * FROM apods ORDER BY timestamp DESC LIMIT 1")
    suspend fun getFirstItem(): ApodDBO?

    @Query("SELECT * FROM apods ORDER BY timestamp ASC LIMIT 1")
    suspend fun getLastItem(): ApodDBO?

    @Query("SELECT * FROM apods ORDER BY timestamp DESC LIMIT 1")
    fun listenToMostRecentApod(): Flow<ApodDBO?>

    @Query("SELECT * FROM apods WHERE timestamp = :timestamp")
    fun listenToApod(timestamp: Long): Flow<ApodDBO>
}