package ru.geogram.data.network

import io.reactivex.Observable
import ru.geogram.entity.entity.LoginModel
import ru.geogram.entity.entity.LoginResponseModel

open class AuthRepository(private val apiService: AuthService) {
    fun auth(loginModel: LoginModel): Observable<LoginResponseModel> {
        return apiService.singIn(loginModel)
    }
}