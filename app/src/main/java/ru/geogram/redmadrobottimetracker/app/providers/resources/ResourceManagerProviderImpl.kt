package ru.geogram.redmadrobottimetracker.app.providers.resources

import android.content.Context
import ru.geogram.domain.providers.resources.ResourceManagerProvider
import javax.inject.Inject

class ResourceManagerProviderImpl @Inject constructor(private val context: Context) : ResourceManagerProvider {

    companion object {
        private const val SHARED_PREFERENCES_NAME_TOKEN = "name_token"
        private const val SHARED_PREFERENCES_TOKEN = "token"
    }

    override fun getString(id: Int): String {
        return context.getString(id)
    }

    @Synchronized
    override fun setToken(preferencesValue: String) {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME_TOKEN, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(SHARED_PREFERENCES_TOKEN, preferencesValue)
        editor.apply()
    }

    @Synchronized
    override fun getToken(): String {
        val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME_TOKEN, Context.MODE_PRIVATE)
        return sharedPreferences.getString(SHARED_PREFERENCES_TOKEN, "")
    }

}