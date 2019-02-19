package ru.geogram.data.network.factory


import ru.geogram.data.network.ServerUrls
import ru.geogram.domain.exceptions.network.NetworkException
import ru.geogram.domain.exceptions.network.ServerException
import java.io.IOException
import javax.inject.Inject

class AppApiFactory @Inject constructor() : ApiFactory(
        ServerUrls.RedMadRobot(),
        HttpClientFactory.okHttpClient {

            // Error interceptor
            addInterceptor { chain ->
                val response = try {
                    chain.proceed(chain.request())
                } catch (th: Throwable) {
                    when(th) {
                        is IOException -> throw NetworkException()
                        else -> throw th
                    }
                }
                if (!response.isSuccessful) {
                    throw ServerException(response.code())
                }
                response
            }
        }
)