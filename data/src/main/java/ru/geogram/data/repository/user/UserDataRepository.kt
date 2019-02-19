package ru.geogram.data.repository.user


import io.reactivex.Single
import ru.geogram.data.model.LoginModel
import ru.geogram.data.model.LoginResponseModel
import ru.geogram.data.model.converter.UserConverter
import ru.geogram.data.network.api.AuthApi
import ru.geogram.data.storage.db.UserDatabaseInterface
import ru.geogram.domain.model.user.LoginPassword
import ru.geogram.domain.model.user.UserInfo
import ru.geogram.domain.providers.rx.SchedulersProvider
import ru.geogram.domain.providers.system.SystemInfoProvider
import ru.geogram.domain.repositories.AuthRepository
import javax.inject.Inject

class UserDataRepository @Inject constructor(
    private val schedulers: SchedulersProvider,
    private val systemInfoProvider: SystemInfoProvider,
    private val authApi: AuthApi,
    private val boxStore: UserDatabaseInterface
) : AuthRepository {
    override fun auth(loginModel: LoginPassword): Single<UserInfo> {
//        val diskObservable = loadFromDb()

        val networkObservable =
            createCall(UserConverter.convertToLoginModel(loginModel))
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.computation())
                .map(this::processResponse)
//                .map(this::saveCallResult)
//                .flatMap { loadFromDb() }
//        val observable = if (systemInfoProvider.hasNetwork()) networkObservable else diskObservable
//                val observable = if (systemInfoProvider.hasNetwork()) networkObservable else diskObservable

        return networkObservable.map<UserInfo> { it }
            .observeOn(schedulers.mainThread())
    }


    private fun createCall(loginModel: LoginModel) = authApi.singIn(loginModel)

    private fun processResponse(response: LoginResponseModel) = UserConverter.fromNetwork(response)

    private fun saveCallResult(source: UserInfo) {
        boxStore.putUser(UserConverter.toDatabase(source))
    }

    private fun loadFromDb() = boxStore.getUsers().map { UserConverter.fromDatabase(it) }
}