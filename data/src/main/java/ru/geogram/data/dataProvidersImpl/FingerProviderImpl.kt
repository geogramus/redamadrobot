package ru.geogram.data.dataProvidersImpl

import android.content.Context
import com.ironz.binaryprefs.BinaryPreferencesBuilder
import ru.geogram.data.Utils
import ru.geogram.domain.providers.dataProviders.FingerProvider
import javax.inject.Inject

class FingerProviderImpl @Inject constructor(private val context: Context) : FingerProvider {

    companion object {
        private const val USING_FINGER = "using_finger"
    }

    override fun setFingerUsing(useFinger: Boolean) {
        Utils.getBinaryEditor(context).putBoolean(USING_FINGER, useFinger).apply()
    }

    override fun canUseFinger(): Boolean {
        return BinaryPreferencesBuilder(context).build().getBoolean(USING_FINGER, false)
    }
}