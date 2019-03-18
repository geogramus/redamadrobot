package ru.geogram.data.network.factory

import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.geogram.data.BuildConfig
import java.util.concurrent.TimeUnit


object HttpClientFactory {

    private const val CONNECT_TIMEOUT_MILLIS = 12000L
    private const val READ_TIMEOUT_MILLIS = 12000L
    private const val CERT_1 = "sha256/919aHK1wjFAZCpJlrIO39suaQnftqM4Mpc24VzVnPE8="
    private const val CERT_2 = "sha256/YLh1dUR9y6Kja30RrAn7JKnbQG/uEtLMkBgFF2Fuihg="
    private const val URL_PATTERN = "watcher.intern.redmadrobot.com"

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
            certificatePinner(
                CertificatePinner.Builder()
                    .add(URL_PATTERN, CERT_1, CERT_2)
                    .build()
            )
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

}
