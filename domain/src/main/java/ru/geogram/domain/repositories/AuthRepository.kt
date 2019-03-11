package ru.geogram.domain.repositories

import io.reactivex.Single
import ru.geogram.domain.model.auth.AuthInfo
import ru.geogram.domain.model.auth.LoginPassword

interface AuthRepository {
    fun auth(loginModel: LoginPassword): Single<AuthInfo>
    fun authCheck(): Single<AuthInfo>
    fun getProfile(): Single<AuthInfo>
}