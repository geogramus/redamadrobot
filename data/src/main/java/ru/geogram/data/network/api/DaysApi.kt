package ru.geogram.data.network.api

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import ru.geogram.data.model.daysresponse.DaysResponse

interface DaysApi {
    @GET("days")
    fun getDays(@Header("Cookie") cookie: String, @Query("from") from: String,
                  @Query("to") to: String): Single<DaysResponse>
}