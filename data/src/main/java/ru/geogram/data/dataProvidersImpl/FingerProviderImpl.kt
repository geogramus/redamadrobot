package ru.geogram.data.dataProvidersImpl

import com.orhanobut.hawk.Hawk
import ru.geogram.domain.providers.dataProviders.FingerProvider

class FingerProviderImpl:FingerProvider {

    companion object {
        private const val USING_FINGER = "using_finger"
    }

    override fun setFingerUsing(useFinger: Boolean) {
        Hawk.put(USING_FINGER, useFinger)
    }

    override fun canUseFinger(): Boolean {
        return Hawk.get<Boolean>(USING_FINGER, false)
    }
}