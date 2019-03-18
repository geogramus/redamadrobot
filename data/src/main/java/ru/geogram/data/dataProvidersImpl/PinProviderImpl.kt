package ru.geogram.data.dataProvidersImpl

import com.orhanobut.hawk.Hawk
import ru.geogram.domain.providers.dataProviders.PinProvider

class PinProviderImpl : PinProvider {

    companion object {
        private const val PIN = "pin"
    }

    override fun setPin(pin: ByteArray) {
        Hawk.put(PIN, pin)
    }

    override fun getPin(): ByteArray {
        return Hawk.get<ByteArray>(PIN, byteArrayOf())
    }
}