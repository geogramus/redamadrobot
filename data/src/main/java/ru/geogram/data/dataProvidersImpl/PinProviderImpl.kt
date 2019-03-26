package ru.geogram.data.dataProvidersImpl

import android.content.Context
import com.ironz.binaryprefs.BinaryPreferencesBuilder
import ru.geogram.data.Utils
import ru.geogram.domain.providers.crypto.TinkProvider
import ru.geogram.domain.providers.dataProviders.PinProvider
import java.nio.charset.Charset
import javax.inject.Inject

class PinProviderImpl @Inject constructor(
    private val context: Context,
    private val tink: TinkProvider
) : PinProvider {

    companion object {
        private const val PIN = "pin"
    }

    override fun setPin(pin: String) {
        val editor = Utils.getBinaryEditor(context)
        editor.putByteArray(PIN, tink.encrypt(pin.toByteArray()))
        editor.apply()
    }

    override fun getPin(): String {
        return tink.decrypt(BinaryPreferencesBuilder(context).build().getByteArray(PIN, byteArrayOf()))
            .toString(Charset.defaultCharset())
    }
}