package ru.geogram.domain.repositories

import io.reactivex.Single
import ru.geogram.domain.model.auth.AuthInfo
import ru.geogram.domain.model.auth.LoginPassword
import ru.geogram.domain.model.auth.UserInfo

interface AuthRepository {
    fun auth(loginModel: LoginPassword): Single<AuthInfo>
}