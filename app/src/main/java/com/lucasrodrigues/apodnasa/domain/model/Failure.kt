package com.lucasrodrigues.apodnasa.domain.model

sealed class Failure : Throwable() {
    object NetworkConnection : Failure()
    class ServerError(val value: String) : Failure()
    class UnknownError(val value: String) : Failure()
}