package ru.geogram.data.network.factory

import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import ru.geogram.data.BuildConfig
import java.util.concurrent.TimeUnit
import java.io.IOException


object HttpClientFactory {

    private const val CONNECT_TIMEOUT_MILLIS = 120000L
    private const val READ_TIMEOUT_MILLIS = 120000L

    fun okHttpClient(builder: OkHttpClient.Builder.() -> Unit): OkHttpClient {
        return getPreConfiguredClientBuilder()
                .apply {
                    builder(this)
                    addInterceptor(getLoggingInterceptor())
                }
                .build()
    }

    private fun getPreConfiguredClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder().apply {
            connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
            readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
        }
    }

    private fun getLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

//    inner class AddCookiesInterceptor : Interceptor {
//        @Throws(IOException::class)
//        override fun intercept(chain: Interceptor.Chain): Response {
//            val builder = chain.request().newBuilder()
//            val preferences = Methods.getCookies(App.getAppContext())
//            for (cookie in preferences) {
//                builder.addHeader("Cookie", cookie)
//                Log.v("OkHttp", "Adding Header: $cookie") // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
//            }
//            return chain.proceed(builder.build())
//        }
//    }
}
