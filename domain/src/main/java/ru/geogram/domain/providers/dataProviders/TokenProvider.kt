package ru.geogram.domain.providers.dataProviders

interface TokenProvider {
    fun setToken(token: String)
    fun getToken(): String
}