package ru.geogram.data.network.api

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import ru.geogram.data.model.network.user.LoginModel
import ru.geogram.data.model.network.user.LoginResponseModel
import ru.geogram.data.model.network.user.RegistrationModel


interface AuthApi {

    @POST("auth/sign-in/")
    fun singIn(@Body loginModel: LoginModel): Single<LoginResponseModel>

    @GET("auth/check/")
    fun authCheck(@Header("Cookie") cookie: String): Single<LoginResponseModel>

    @GET("/auth/profile/")
    fun getProfile(@Header("Cookie") cookie: String): Single<LoginResponseModel>

    @POST("auth/sign-up/")
    fun singUp(@Body registrationModel: RegistrationModel): Single<LoginResponseModel>
}