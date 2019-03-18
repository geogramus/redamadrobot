package ru.geogram.domain.providers.crypto

import com.google.crypto.tink.Aead

interface TinkProvider {
    fun provideTink(): Aead
}