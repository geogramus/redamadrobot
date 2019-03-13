package ru.geogram.data.dataProvidersImpl

import com.orhanobut.hawk.Hawk
import ru.geogram.domain.providers.dataProviders.TokenProvider

class TokenProviderImpl : TokenProvider {

    companion object {
        private const val TOKEN = "token"
    }

    @Synchronized
    override fun setToken(token: String) {
        Hawk.put(TOKEN, token)
    }

    override fun getToken(): String {
        return Hawk.get<String>(TOKEN, "")
    }
}