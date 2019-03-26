package ru.geogram.domain.providers.dataProviders

interface PinProvider {
    fun setPin(pin: String)
    fun getPin(): String
}