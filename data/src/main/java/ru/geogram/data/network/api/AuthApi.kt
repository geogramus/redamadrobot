package ru.geogram.data.network.api

import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ru.geogram.data.model.LoginModel
import ru.geogram.data.model.LoginResponseModel
import com.google.gson.GsonBuilder
import io.reactivex.Single


interface AuthApi {

    @POST("auth/sign-in/")
    fun singIn(@Body loginModel: LoginModel): Single<LoginResponseModel>


//    companion object Factory {
//
//        private const val apiVersion = "v1"
//
//        fun create(): AuthApi {
//
//            val httpClient = OkHttpClient.Builder()
//            val logging = HttpLoggingInterceptor()
//            logging.level = HttpLoggingInterceptor.Level.BODY
//            httpClient.addInterceptor(logging)
//            val client = httpClient.build()
//
//            val gsonBuilder = GsonBuilder()
//            gsonBuilder.setLenient()
//            val gson = gsonBuilder.create()
//
//            val retrofit = Retrofit.Builder()
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .baseUrl(getUrl())
//                .client(client)
//                .build()
//
//            return retrofit.create(AuthApi::class.java)
//        }
//
//        private fun getUrl(): String {
//            return "https://watcher.intern.redmadrobot.com/api/v1/"
//        }
//    }
}