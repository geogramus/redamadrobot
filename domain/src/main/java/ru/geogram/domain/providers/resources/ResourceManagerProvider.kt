package ru.geogram.domain.providers.resources

import android.content.Context
import ru.geogram.domain.model.auth.LoginPassword

interface ResourceManagerProvider {
    fun getString(id: Int): String
    fun setToken(preferencesValue: String)
    fun getToken(): String
    fun setLoginPassword(loginModel: LoginPassword)
    fun getLoginPassword(): LoginPassword
}