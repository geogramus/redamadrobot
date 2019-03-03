package ru.geogram.data.repository.days

import io.reactivex.Single
import ru.geogram.data.model.converter.DaysConverter
import ru.geogram.data.model.network.daysresponse.DaysResponse
import ru.geogram.data.network.api.DaysApi
import ru.geogram.domain.model.days.DaysInfo
import ru.geogram.domain.providers.resources.ResourceManagerProvider
import ru.geogram.domain.providers.rx.SchedulersProvider
import ru.geogram.domain.providers.system.SystemInfoProvider
import ru.geogram.domain.repositories.DaysRepository

class DaysDataRepository(
    val schedulers: SchedulersProvider,
    private val systemInfoProvider: SystemInfoProvider,
    private val daysApi: DaysApi,
//private val boxStore: UserDatabaseInterface,
    private val resourceManager: ResourceManagerProvider
) : DaysRepository {
    override fun getDays(from: String, to: String): Single<DaysInfo> {
        val cookie = resourceManager.getToken()
        val networkObservable =
            getDayInfo(cookie, from, to)
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.computation())
                .map(this::processResponse)

        return networkObservable
    }

    private fun getDayInfo(cookie: String, from: String, to: String) = daysApi.getDays(cookie, from, to)
    private fun processResponse(response: DaysResponse) = DaysConverter.fromNetwork(response)

}