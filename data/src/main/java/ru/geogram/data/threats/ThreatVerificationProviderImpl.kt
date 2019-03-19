package ru.geogram.data.threats

import android.content.Context
import android.net.wifi.WifiManager
import com.scottyab.rootbeer.RootBeer
import ru.geogram.data.R
import ru.geogram.domain.providers.threats.ThreatVerificationProvider
import javax.inject.Inject

class ThreatVerificationProviderImpl @Inject constructor(
    context: Context
) : ThreatVerificationProvider {

    companion object {
        private const val PUBLIC = "public"
        private const val FREE = "free"
        private const val OPEN = "open"
        private const val AIRPORT = "airport"
        private const val GUEST = "GUEST"
    }

    private val ROOT_THREAT by lazy {
        context.getString(R.string.root_threat)
    }

    private val PUBLIC_WIFI_THREAT by lazy {
        context.getString(R.string.public_wifi_threat)
    }

    private val wifiManager by lazy {
        context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }

    private val wifiTypeList by lazy {
        listOf(PUBLIC, FREE, OPEN, AIRPORT, GUEST)
    }

    private val rootBeer by lazy {
        RootBeer(context)
    }

    private val threatsList by lazy {
        mutableListOf<String>()
    }

    override fun getThreats(): List<String> {
        threatsList.clear()
        detectPublicWifi()
        detectRoot()
        return threatsList
    }

    private fun detectRoot() {
        if (rootBeer.isRootedWithoutBusyBoxCheck) {
            threatsList.add(ROOT_THREAT)
        }
    }


    private fun detectPublicWifi() {
        wifiManager.connectionInfo?.let { info ->
            val ssid = info.ssid
            if (wifiTypeList.any { ssid.toLowerCase().contains(it) }) {
                threatsList.add(PUBLIC_WIFI_THREAT)
                return
            }
            if (wifiManager.configuredNetworks
                    .find { it.SSID == ssid }
                    ?.allowedKeyManagement
                    ?.isEmpty == true
            ) {
                threatsList.add(PUBLIC_WIFI_THREAT)
            }
        }
    }
}