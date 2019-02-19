package ru.geogram.domain.repositories

import io.reactivex.Single
import ru.geogram.domain.model.user.LoginPassword
import ru.geogram.domain.model.user.UserInfo

interface AuthRepository {
    fun auth(loginModel: LoginPassword): Single<UserInfo>
}