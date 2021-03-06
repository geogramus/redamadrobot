package ru.geogram.redmadrobottimetracker.app.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import ru.geogram.domain.providers.dataProviders.FingerProvider
import ru.geogram.domain.providers.dataProviders.PinProvider
import ru.geogram.redmadrobottimetracker.app.presentation.ShowMainScreenFragment
import ru.geogram.redmadrobottimetracker.app.providers.navigation.RouterProvider
import javax.inject.Inject

class EnterPinViewModel @Inject constructor(
    private val provider: RouterProvider,
    private val pinProvider: PinProvider,
    private val fingerProvider: FingerProvider
) : BaseViewModel() {

    private val rightPinLength = 4
    val isValidPin = MutableLiveData<Boolean>()
    val usingFingerScan = MutableLiveData<Boolean>()

    fun isPinValid(pin: String) {
        if (pin.length == rightPinLength) {
            val nn = pinProvider.getPin()
            isValidPin.value = nn == pin
        }
    }

    fun onResume() = usingFingerScan.postValue(fingerProvider.canUseFinger())

    fun showMainScreen() = provider.provideRouter().newRootScreen(ShowMainScreenFragment)
}