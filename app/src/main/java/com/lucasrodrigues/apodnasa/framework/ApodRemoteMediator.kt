package com.lucasrodrigues.apodnasa.framework

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.lucasrodrigues.apodnasa.domain.model.dbo.ApodDBO
import com.lucasrodrigues.apodnasa.domain.repository.ApodRepository
import com.lucasrodrigues.apodnasa.extensions.minusDays
import java.util.*

@ExperimentalPagingApi
class ApodRemoteMediator(
    private val apodRepository: ApodRepository,
) : RemoteMediator<Int, ApodDBO>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ApodDBO>
    ): MediatorResult {
        return try {
            val referenceDate = when (loadType) {
                REFRESH -> {
                    getKeyClosestToCurrentPosition(state = state) ?: Date()
                }
                PREPEND -> getKeyForFirstItem(state) ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                APPEND -> getKeyForLastItem(state) ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )
            }
            MediatorResult.Success(endOfPaginationReached = true)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private fun getKeyForLastItem(
        state: PagingState<Int, ApodDBO>
    ): Date? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()
            ?.let { apodDBO ->
                Date(apodDBO.timestamp)
            }
    }

    private fun getKeyForFirstItem(
        state: PagingState<Int, ApodDBO>
    ): Date? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()
            ?.let { apodDBO ->
                Date(apodDBO.timestamp).minusDays(state.config.pageSize)
            }
    }

    private fun getKeyClosestToCurrentPosition(
        state: PagingState<Int, ApodDBO>
    ): Date? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let { apodDBO ->
                Date(apodDBO.timestamp)
            }
        }
    }
}