package ru.geogram.data.repository.auth


import io.reactivex.Single
import ru.geogram.data.model.converter.AuthConverter
import ru.geogram.data.model.network.user.LoginResponseModel
import ru.geogram.data.network.api.AuthApi
import ru.geogram.domain.exceptions.network.CreditinalException
import ru.geogram.domain.model.auth.AuthInfo
import ru.geogram.domain.model.auth.LoginPassword
import ru.geogram.domain.model.auth.RegistraionInfo
import ru.geogram.domain.providers.dataProviders.TokenProvider
import ru.geogram.domain.providers.dataProviders.UserCredentialsProvider
import ru.geogram.domain.providers.system.SystemInfoProvider
import ru.geogram.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthDataRepository @Inject constructor(
    private val systemInfoProvider: SystemInfoProvider,
    private val authApi: AuthApi,
    private val tokenProvider: TokenProvider,
    private val userCredentialsProvider: UserCredentialsProvider
) : AuthRepository {

    override fun getProfile(): Single<AuthInfo> {
        val cookie = tokenProvider.getToken()
        return authApi.getProfile(cookie)
            .map(this::processResponse)

    }

    override fun authCheck(): Single<AuthInfo> {
        val cookie = tokenProvider.getToken()
        return authApi.authCheck(cookie)
            .map(this::processResponse)
            .onErrorResumeNext(::convertException)
    }

    private fun convertException(error: Throwable): Single<AuthInfo> {
        if (error is CreditinalException) {
            return auth(userCredentialsProvider.getLoginPassword())
        } else {
            throw error
        }
    }

    override fun auth(loginModel: LoginPassword): Single<AuthInfo> {
        return authApi.singIn(AuthConverter.convertToLoginModel(loginModel))
            .map(this::processResponse)
    }


    override fun registrate(registrationInfo: RegistraionInfo): Single<AuthInfo> {
        return authApi.singUp(AuthConverter.convertToRegistrationModel(registrationInfo))
                .map(this::processResponse)
    }

    private fun processResponse(response: LoginResponseModel) = AuthConverter.fromNetwork(response)

}