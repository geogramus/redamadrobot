package ru.geogram.data.network.api

import retrofit2.http.*
import ru.geogram.data.model.LoginModel
import ru.geogram.data.model.LoginResponseModel
import io.reactivex.Single


interface AuthApi {

    @POST("auth/sign-in/")
    fun singIn(@Body loginModel: LoginModel): Single<LoginResponseModel>

    @GET("auth/check/")
    fun authCheck(@Header("Cookie") cookie: String): Single<LoginResponseModel>

    @GET("/auth/profile/")
    fun getProfile(@Header("Cookie") cookie: String): Single<LoginResponseModel>
}