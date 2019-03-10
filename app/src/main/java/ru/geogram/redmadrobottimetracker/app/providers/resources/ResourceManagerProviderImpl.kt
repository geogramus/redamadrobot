package ru.geogram.redmadrobottimetracker.app.providers.resources

import android.content.Context
import android.content.SharedPreferences
import ru.geogram.domain.model.auth.LoginPassword
import ru.geogram.domain.providers.resources.ResourceManagerProvider
import javax.inject.Inject

class ResourceManagerProviderImpl @Inject constructor(private val context: Context) : ResourceManagerProvider {


    companion object {
        private const val SHARED_PREFERENCES_RED_MAD_ROBOT = "name_token"
        private const val SHARED_PREFERENCES_TOKEN = "token"
        private const val SHARED_PREFERENCES_LOGIN = "login"
        private const val SHARED_PREFERENCES_PASSWORD = "password"
    }

    @Volatile
    private var sharedPreferences: SharedPreferences? = null

    override fun getString(id: Int): String {
        return context.getString(id)
    }

    private fun getSharedPrederences(): SharedPreferences? {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_RED_MAD_ROBOT, Context.MODE_PRIVATE)
        }
        return sharedPreferences
    }


    @Synchronized
    override fun setToken(preferencesValue: String) {
        val editor = getSharedPrederences()?.edit()
        editor?.putString(SHARED_PREFERENCES_TOKEN, preferencesValue)
        editor?.apply()
    }


    override fun getToken(): String {
        return getSharedPrederences()!!.getString(SHARED_PREFERENCES_TOKEN, "")
    }

    @Synchronized
    override fun setLoginPassword(loginModel: LoginPassword) {
        val editor = getSharedPrederences()?.edit()
        editor?.putString(SHARED_PREFERENCES_LOGIN, loginModel.login)
        editor?.putString(SHARED_PREFERENCES_PASSWORD, loginModel.password)
        editor?.apply()
    }

    override fun getLoginPassword(): LoginPassword {
        val login = getSharedPrederences()!!.getString(SHARED_PREFERENCES_LOGIN, "")
        val password = getSharedPrederences()!!.getString(SHARED_PREFERENCES_PASSWORD, "")
        return LoginPassword(login, password)
    }
}