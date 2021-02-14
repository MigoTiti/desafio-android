package com.lucasrodrigues.apodnasa.domain.interactor

import androidx.paging.PagingData
import com.lucasrodrigues.apodnasa.domain.model.Apod
import com.lucasrodrigues.apodnasa.domain.repository.ApodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListenToPreviousDaysApods @Inject constructor(
    private val apodRepository: ApodRepository,
) : UseCase<PagingData<Apod>, Unit>() {

    override fun run(params: Unit): Flow<PagingData<Apod>> {
        return apodRepository.getApodPaginatedList()
    }
}