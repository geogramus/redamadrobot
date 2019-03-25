package ru.geogram.domain.model.auth

data class RegistraionInfo(
        val firstName: String,
        val lastName: String,
        val email: String,
        val password: String
)