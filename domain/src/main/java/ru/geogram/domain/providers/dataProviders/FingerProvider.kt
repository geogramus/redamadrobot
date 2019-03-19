package ru.geogram.domain.providers.dataProviders

interface FingerProvider {
    fun setFingerUsing(useFinger: Boolean)
    fun canUseFinger(): Boolean
}