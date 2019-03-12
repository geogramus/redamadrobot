package ru.geogram.redmadrobottimetracker.app.providers.resources

import android.content.Context
import com.google.gson.Gson
import com.orhanobut.hawk.Hawk
import ru.geogram.domain.model.auth.LoginPassword
import ru.geogram.domain.providers.resources.ResourceManagerProvider
import javax.inject.Inject

class ResourceManagerProviderImpl @Inject constructor(private val context: Context) : ResourceManagerProvider {


    companion object {
        private const val SHARED_PREFERENCES_RED_MAD_ROBOT = "name_token"
        private const val TOKEN = "token"
        private const val LOGIN = "login"
    }


    override fun getString(id: Int): String {
        return context.getString(id)
    }


    @Synchronized
    override fun setToken(token: String) {
        Hawk.put(TOKEN, token)
    }


    override fun getToken(): String {
        return Hawk.get<String>(TOKEN, "")
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