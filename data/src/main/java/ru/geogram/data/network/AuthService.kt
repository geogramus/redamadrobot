package ru.geogram.data.network

import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ru.geogram.entity.entity.LoginModel
import ru.geogram.entity.entity.LoginResponseModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder


interface AuthService {

    //    @POST("api/$apiVersion/cmn/devices/{imei}/settings")
//    fun sendSettings(@Path(value = "imei") imei: String,
//                     @Header("Content-Type") contentType: String = "application/json",
//                     @Body settings: HashMap<String, String>): Observable<Result<Void>>
//
//    @GET("api/$apiVersion/cmn/devices/{imei}/settings")
//    fun getSettings(@Path(value = "imei") imei: String): Observable<HashMap<String, String>>
//
//    @POST("api/$apiVersion/cmn/unbinddevice")
//    fun logOut(@Body device: LogoutRequest): Observable<Result<Void>>
    @POST("auth/sign-in/")
    fun singIn(@Body device: LoginModel): Observable<LoginResponseModel>

    /**
     * Companion object to create the MothershipService
     */
    companion object Factory {

        private const val apiVersion = "v1"

        fun create(): AuthService {

            val httpClient = OkHttpClient.Builder()
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
            val client = httpClient.build()

            val gsonBuilder = GsonBuilder()
            gsonBuilder.setLenient()
            val gson = gsonBuilder.create()

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(getUrl())
                .client(client)
                .build()

            return retrofit.create(AuthService::class.java)
        }

        private fun getUrl(): String {
            return "https://watcher.intern.redmadrobot.com/api/v1/"
        }
    }
}