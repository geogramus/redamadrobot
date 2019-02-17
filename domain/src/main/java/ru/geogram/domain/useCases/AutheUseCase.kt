package ru.geogram.domain.useCases

import io.reactivex.Observable
import ru.geogram.entity.entity.LoginModel
import ru.geogram.entity.entity.LoginResponseModel
import ru.geogram.data.network.AuthRepositoryProvider

class AutheUseCase:AuthUseCaseInterface {
    override fun auth(model: LoginModel): Observable<LoginResponseModel> {
        return AuthRepositoryProvider.provideAuthRepository().auth(model)
    }
}