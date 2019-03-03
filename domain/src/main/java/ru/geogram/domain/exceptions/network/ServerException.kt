package ru.geogram.domain.exceptions.network

open class ServerException(val code: Int) : RuntimeException("error code=$code")
