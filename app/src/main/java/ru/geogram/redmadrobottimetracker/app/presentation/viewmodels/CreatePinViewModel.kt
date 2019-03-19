package ru.geogram.redmadrobottimetracker.app.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import ru.geogram.domain.providers.crypto.TinkProvider
import ru.geogram.domain.providers.dataProviders.FingerProvider
import ru.geogram.domain.providers.dataProviders.PinProvider
import ru.geogram.redmadrobottimetracker.app.presentation.ShowMainScreenFragment
import ru.geogram.redmadrobottimetracker.app.providers.navigation.RouterProvider
import javax.inject.Inject

class CreatePinViewModel @Inject constructor(
        private val provider: RouterProvider,
        private val tink: TinkProvider,
        private val pinProvider: PinProvider,
        private val fingerProvider: FingerProvider
) : BaseViewModel() {

    private val rightPinLength = 4
    val isValidPin = MutableLiveData<Boolean>()

    fun isPinValid(pin: String) = isValidPin.postValue(pin.length == rightPinLength)

    fun saveUseFingerSetting(useFinger:Boolean) = fingerProvider.setFingerUsing(useFinger)

    fun savePin(pin: String) = pinProvider.setPin(tink.provideTink().encrypt(pin.toByteArray()
            , byteArrayOf()))

    fun showMainScreen() = provider.provideRouter().newRootScreen(ShowMainScreenFragment)
}