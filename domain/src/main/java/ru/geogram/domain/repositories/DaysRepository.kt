package ru.geogram.domain.repositories

import io.reactivex.Single
import ru.geogram.domain.model.days.DaysInfo

interface DaysRepository {
    fun getDays(from: String, to: String): Single<DaysInfo>
}