package ru.geogram.redmadrobottimetracker.app.providers.crypto

import android.content.Context
import com.google.crypto.tink.Aead
import com.google.crypto.tink.Config
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.aead.AeadKeyTemplates
import com.google.crypto.tink.config.TinkConfig
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import ru.geogram.domain.providers.crypto.TinkProvider
import java.io.IOException
import java.security.GeneralSecurityException
import javax.inject.Inject


class TinkProviderImpl @Inject constructor(private val context: Context) : TinkProvider {

    private val PREF_FILE_NAME = "red_mad_watcher_pref"
    private val TINK_KEYSET_NAME = "red_mad_watcher__keyset"
    private val MASTER_KEY_URI = "android-keystore://red_mad_watcher_key"
    var aead: Aead? = null

    override fun provideTink() {
        try {
            Config.register(TinkConfig.LATEST)
//            aead = getOrGenerateNewKeysetHandle(context).getPrimitive(Aead::class.java)
        } catch (e: GeneralSecurityException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    @Throws(IOException::class, GeneralSecurityException::class)
    private fun getOrGenerateNewKeysetHandle(applicationContext: Context): KeysetHandle {
        return AndroidKeysetManager.Builder()
            .withSharedPref(applicationContext, TINK_KEYSET_NAME, PREF_FILE_NAME)
            .withKeyTemplate(AeadKeyTemplates.AES256_GCM)
            .withMasterKeyUri(MASTER_KEY_URI)
            .build()
            .getKeysetHandle()
    }
}