package ru.geogram.data.dataProvidersImpl

import com.google.gson.Gson
import com.orhanobut.hawk.Hawk
import ru.geogram.domain.model.auth.LoginPassword
import ru.geogram.domain.providers.dataProviders.LoginPasswordProvider

class LoginPasswordProviderImpl : LoginPasswordProvider {

    companion object {
        private const val LOGIN = "login"
    }

    @Synchronized
    override fun setLoginPassword(loginModel: LoginPassword) {
        Hawk.put(LOGIN, Gson().toJson(loginModel))
    }

    override fun getLoginPassword(): LoginPassword {
        return Gson().fromJson(
            Hawk.get<String>(LOGIN, ""),
            LoginPassword::class.java
        )
    }
}