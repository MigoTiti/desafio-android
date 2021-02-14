package com.lucasrodrigues.apodnasa.domain.interactor

import com.lucasrodrigues.apodnasa.domain.model.Apod
import com.lucasrodrigues.apodnasa.domain.repository.ApodRepository
import kotlinx.coroutines.flow.Flow

class ListenToApod(
    private val apodRepository: ApodRepository,
) : UseCase<Apod, ListenToApod.Params>() {

    override fun run(params: Params): Flow<Apod> {
        return apodRepository.getApod(params.timestamp)
    }

    class Params(
        val timestamp: Long,
    )
}