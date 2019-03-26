package ru.geogram.data.dataProvidersImpl

import android.content.Context
import com.ironz.binaryprefs.BinaryPreferencesBuilder
import ru.geogram.data.Utils
import ru.geogram.domain.model.auth.LoginPassword
import ru.geogram.domain.providers.crypto.TinkProvider
import ru.geogram.domain.providers.dataProviders.UserCredentialsProvider
import java.nio.charset.Charset
import javax.inject.Inject


class UserCredentialsProviderImpl @Inject constructor(
    private val context: Context,
    private val tink: TinkProvider
) : UserCredentialsProvider {

    companion object {
        private const val LOGIN = "login"
        private const val PASSWORD = "password"

    }

    @Synchronized
    override fun setLoginPassword(loginModel: LoginPassword) {
        val editor = Utils.getBinaryEditor(context)
        editor.putByteArray(LOGIN, tink.encrypt(loginModel.login.toByteArray()))
        editor.putByteArray(PASSWORD, tink.encrypt(loginModel.password.toByteArray()))
        editor.apply()
    }

    override fun getLoginPassword(): LoginPassword {
        val builder = BinaryPreferencesBuilder(context).build()
        return LoginPassword(
            tink.decrypt(builder.getByteArray(LOGIN, byteArrayOf())).toString(Charset.defaultCharset()),
            tink.decrypt(builder.getByteArray(PASSWORD, byteArrayOf())).toString(Charset.defaultCharset())
        )
    }

}