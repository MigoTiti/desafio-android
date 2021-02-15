package com.lucasrodrigues.apodnasa.domain.interactor

import com.lucasrodrigues.apodnasa.domain.model.Apod
import com.lucasrodrigues.apodnasa.domain.repository.ApodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListenToTodayApod @Inject constructor(
    private val apodRepository: ApodRepository,
) : UseCase<Apod?, Unit>() {
    override fun run(params: Unit): Flow<Apod?> {
        return apodRepository.getTodayApod()
    }
}