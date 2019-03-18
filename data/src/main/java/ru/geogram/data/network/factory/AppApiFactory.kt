package ru.geogram.data.network.factory


import ru.geogram.data.model.network.getErrorMessage
import ru.geogram.data.network.ServerUrls
import ru.geogram.domain.exceptions.network.CreditinalException
import ru.geogram.domain.exceptions.network.NetworkException
import ru.geogram.domain.exceptions.network.ServerException
import ru.geogram.domain.providers.dataProviders.TokenProvider
import java.io.IOException
import javax.inject.Inject


class AppApiFactory @Inject constructor(private val tokenProvider: TokenProvider) : ApiFactory(
    ServerUrls.RedMadRobot(),
    HttpClientFactory.okHttpClient {

        // Error interceptor
        addInterceptor { chain ->
            val response = try {
                chain.proceed(chain.request())
            } catch (th: Throwable) {
                when (th) {
                    is IOException -> throw NetworkException()
                    else -> throw th
                }
            }
            when (response.code()) {
                401 -> {
                    throw CreditinalException()
                }
                in 400..501 -> {
                    throw ServerException(response.code(), getErrorMessage(response.body()))
                }
                else -> {

                }
            }
            if (!response.isSuccessful) {
                throw ServerException(response.code())
            }
            response
        }
        addInterceptor { chain ->
            val response = chain.proceed(chain.request())
            if (tokenProvider.getToken().isEmpty()) {
                if (!response.headers(SET_COOKIE).isEmpty()) {
                    var cookies = ""
                    for (header in response.headers(SET_COOKIE)) {
                        cookies = header
                    }
                    // Save the cookies (I saved in SharedPrefrence), you save whereever you want to save
                    tokenProvider.setToken(cookies)
                }
            }
            response
        }

    }
) {
    companion object {
        private const val SET_COOKIE = "set-cookie"
    }
}