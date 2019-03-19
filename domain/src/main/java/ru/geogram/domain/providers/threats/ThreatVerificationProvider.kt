package ru.geogram.domain.providers.threats

interface ThreatVerificationProvider {
    fun getThreats():List<String>
}