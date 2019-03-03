package ru.geogram.domain.model.auth

data class UserInfo(
    var first_name: String? = "",
    var role: String? = "",
    var last_name: String? = "",
    var email: String? = "",
    var id: Int? = 0,
    var is_staff: Boolean? = false
)

