package ru.geogram.domain.model.auth

data class AuthInfo(
    var userInfo: UserInfo? = null,
    var errorInfo: ErrorInfo? = null
)


