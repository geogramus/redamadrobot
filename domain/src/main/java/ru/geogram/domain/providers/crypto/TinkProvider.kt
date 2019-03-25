package ru.geogram.domain.providers.crypto

interface TinkProvider {
    fun encrypt(pin: ByteArray): ByteArray
    fun decrypt(pin: ByteArray): ByteArray
}