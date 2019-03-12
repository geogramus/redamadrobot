package ru.geogram.domain.exceptions.network

open class ServerException(val code: Int, val errorMessage: String? = null) :
    RuntimeException("error code=$code, message=$errorMessage")
