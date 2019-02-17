package ru.geogram.domain.useCases

import io.reactivex.Observable
import ru.geogram.entity.entity.LoginModel
import ru.geogram.entity.entity.LoginResponseModel

interface AuthUseCaseInterface {
    fun auth(model: LoginModel):Observable<LoginResponseModel>
}