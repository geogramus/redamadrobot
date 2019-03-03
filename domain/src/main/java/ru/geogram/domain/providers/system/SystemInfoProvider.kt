package ru.geogram.domain.providers.system

interface SystemInfoProvider {
    fun hasNetwork(): Boolean
}