package ru.geogram.data.network.factory


import ru.geogram.data.network.ServerUrls
import ru.geogram.domain.exceptions.network.NetworkException
import ru.geogram.domain.exceptions.network.ServerException
import ru.geogram.domain.providers.resources.ResourceManagerProvider
import java.io.IOException
import javax.inject.Inject
import ru.geogram.data.model.network.getErrorMessage
import ru.geogram.domain.exceptions.network.ConvertException


class AppApiFactory @Inject constructor(private val resourceManager: ResourceManagerProvider) : ApiFactory(
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
                        throw ConvertException(getErrorMessage(response.body()))
                    }
                    in 400..501 -> {
                        throw ConvertException(getErrorMessage(response.body()))
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
                if (resourceManager.getToken().isEmpty()) {
                    if (!response.headers("set-cookie").isEmpty()) {
                        var cookies = ""
                        for (header in response.headers("set-cookie")) {
                            cookies = header
                        }
                        // Save the cookies (I saved in SharedPrefrence), you save whereever you want to save
                        resourceManager.setToken(cookies)
                    }
                }
                response
            }

        }
)