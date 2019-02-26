package ru.geogram.data.network.api

import io.reactivex.Single
import retrofit2.http.*
import ru.geogram.data.model.network.daysresponse.DaysResponse
import ru.geogram.data.model.network.projects.PayLoad
import ru.geogram.data.model.network.projects.PayloadResponse
import ru.geogram.data.model.network.projects.Projects

interface ProjectsApi {
    @GET("projects/")
    fun getProjects(@Header("Cookie") cookie: String, @Query("recent") recent: Boolean)
            : Single<Projects>
    @POST("days")
    fun singIn(@Header("Cookie") cookie: String, @Body payload: PayLoad): Single<PayloadResponse>
}