package com.lucasrodrigues.apodnasa.domain.interactor

import kotlinx.coroutines.flow.Flow

abstract class UseCase<out Return, in Params> {
    abstract fun run(params: Params): Flow<Return>

    operator fun invoke(params: Params): Flow<Return> {
        return run(params)
    }
}
