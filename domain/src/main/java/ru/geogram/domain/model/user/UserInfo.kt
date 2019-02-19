package ru.geogram.domain.model.user

data class UserInfo(
    var first_name: String? = "",
    var role: String? = "",
    var last_name: String? = "",
    var email: String? = "",
    var id: Int,
    var is_staff: String? = ""
)

