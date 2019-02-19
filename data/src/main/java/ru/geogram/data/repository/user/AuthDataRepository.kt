package ru.geogram.data.repository.user


import io.reactivex.Single
import ru.geogram.data.model.LoginModel
import ru.geogram.data.model.LoginResponseModel
import ru.geogram.data.model.converter.AuthConverter
import ru.geogram.data.network.api.AuthApi
import ru.geogram.data.storage.db.UserDatabaseInterface
import ru.geogram.domain.model.auth.AuthInfo
import ru.geogram.domain.model.auth.LoginPassword
import ru.geogram.domain.model.auth.UserInfo
import ru.geogram.domain.providers.rx.SchedulersProvider
import ru.geogram.domain.providers.system.SystemInfoProvider
import ru.geogram.domain.repositories.AuthRepository
import javax.inject.Inject

class AuthDataRepository @Inject constructor(
    private val schedulers: SchedulersProvider,
    private val systemInfoProvider: SystemInfoProvider,
    private val authApi: AuthApi,
    private val boxStore: UserDatabaseInterface
) : AuthRepository {
    override fun auth(loginModel: LoginPassword): Single<AuthInfo> {
//        val diskObservable = loadFromDb()

        val networkObservable =
            createCall(AuthConverter.convertToLoginModel(loginModel))
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.computation())
                .map(this::processResponse)
//                .map(this::saveCallResult)
//                .flatMap { loadFromDb() }
//        val observable = if (systemInfoProvider.hasNetwork()) networkObservable else diskObservable
//                val observable = if (systemInfoProvider.hasNetwork()) networkObservable else diskObservable

        return networkObservable.map<AuthInfo> { it }
            .observeOn(schedulers.mainThread())
    }


    private fun createCall(loginModel: LoginModel) = authApi.singIn(loginModel)

    private fun processResponse(response: LoginResponseModel) = AuthConverter.fromNetwork(response)

    private fun saveCallResult(source: UserInfo) {
        boxStore.putUser(AuthConverter.toDatabase(source))
    }

    private fun loadFromDb() = boxStore.getUsers().map { AuthConverter.fromDatabase(it) }
}