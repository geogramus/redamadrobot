package ru.geogram.data.repository.auth


import io.reactivex.Single
import ru.geogram.data.model.converter.AuthConverter
import ru.geogram.data.model.network.user.LoginModel
import ru.geogram.data.model.network.user.LoginResponseModel
import ru.geogram.data.network.api.AuthApi
import ru.geogram.domain.exceptions.network.CreditinalException
import ru.geogram.domain.model.auth.AuthInfo
import ru.geogram.domain.model.auth.LoginPassword
import ru.geogram.domain.providers.resources.ResourceManagerProvider
import ru.geogram.domain.providers.rx.SchedulersProvider
import ru.geogram.domain.providers.system.SystemInfoProvider
import ru.geogram.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthDataRepository @Inject constructor(
    private val schedulers: SchedulersProvider,
    private val systemInfoProvider: SystemInfoProvider,
    private val authApi: AuthApi,
    private val resourceManager: ResourceManagerProvider
) : AuthRepository {

    override fun getProfile(): Single<AuthInfo> {
        val cookie = resourceManager.getToken()
        return getProfileInfo(cookie)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.computation())
            .map(this::processResponse)
            .observeOn(schedulers.mainThread())

    }

    override fun authCheck(): Single<AuthInfo> {
        val cookie = resourceManager.getToken()
        return authCheckCall(cookie)
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.computation())
            .map(this::processResponse)
            .onErrorResumeNext(::convertException)
    }

    private fun convertException(th: Throwable): Single<AuthInfo> {
        if (th is CreditinalException) {
            return auth(resourceManager.getLoginPassword())
        } else {
            throw th
        }
    }

    override fun auth(loginModel: LoginPassword): Single<AuthInfo> {

        return createCall(AuthConverter.convertToLoginModel(loginModel))
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.computation())
            .map(this::processResponse)
            .observeOn(schedulers.mainThread())


    }

    private fun authCheckCall(cookie: String) = authApi.authCheck(cookie)

    private fun getProfileInfo(cookie: String) = authApi.getProfile(cookie)

    private fun createCall(loginModel: LoginModel) = authApi.singIn(loginModel)

    private fun processResponse(response: LoginResponseModel) = AuthConverter.fromNetwork(response)


}