package ru.geogram.redmadrobottimetracker.app.providers.system

import android.content.Context
import android.net.ConnectivityManager
import ru.geogram.domain.providers.system.SystemInfoProvider
import javax.inject.Inject

class SystemInfoDataProvider @Inject constructor(private val context: Context) : SystemInfoProvider {

    override fun hasNetwork(): Boolean {
        val appContext = context.applicationContext
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        return connectivityManager?.activeNetworkInfo?.isConnected ?: false
    }
}