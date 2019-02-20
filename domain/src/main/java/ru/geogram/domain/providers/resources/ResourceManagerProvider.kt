package ru.geogram.domain.providers.resources

interface ResourceManagerProvider {
    fun getString(id: Int): String
}