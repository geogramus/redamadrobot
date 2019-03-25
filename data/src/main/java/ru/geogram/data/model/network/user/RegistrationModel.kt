package ru.geogram.data.model.network.user

data class RegistrationModel(
        val first_name: String,
        val password: String,
        val last_name: String,
        val email: String
)