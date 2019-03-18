package ru.geogram.domain.providers.dataProviders

interface PinProvider {
    fun setPin(pin: ByteArray)
    fun getPin(): ByteArray
}