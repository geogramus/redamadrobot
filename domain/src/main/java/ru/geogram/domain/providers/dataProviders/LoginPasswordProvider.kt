package ru.geogram.domain.providers.dataProviders

import ru.geogram.domain.model.auth.LoginPassword

interface LoginPasswordProvider {
    fun setLoginPassword(loginModel: LoginPassword)
    fun getLoginPassword(): LoginPassword
}