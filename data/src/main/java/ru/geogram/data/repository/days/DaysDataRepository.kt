package ru.geogram.data.repository.days

import io.reactivex.Single
import ru.geogram.data.model.converter.DaysConverter
import ru.geogram.data.network.api.DaysApi
import ru.geogram.domain.model.days.DaysInfo
import ru.geogram.domain.providers.dataProviders.TokenProvider
import ru.geogram.domain.repositories.DaysRepository

class DaysDataRepository(
    private val daysApi: DaysApi,
    private val tokenProvider: TokenProvider
) : DaysRepository {
    override fun getDays(from: String, to: String): Single<DaysInfo> {
        val cookie = tokenProvider.getToken()
        val networkObservable = daysApi.getDays(cookie, from, to)
            .map {DaysConverter.fromNetwork(it)}
        return networkObservable
    }

}