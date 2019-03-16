package ru.geogram.redmadrobottimetracker.app.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import ru.geogram.redmadrobottimetracker.app.presentation.ShowMainScreenFragment
import ru.geogram.redmadrobottimetracker.app.providers.navigation.RouterProvider
import javax.inject.Inject

class CreatePinViewModel @Inject constructor(private val provider: RouterProvider)
    : BaseViewModel() {

    private val rightPinLength = 4
    val isValidPin = MutableLiveData<Boolean>()

    fun isPinValid(pin: String) = isValidPin.postValue(pin.length == rightPinLength)

    fun showMainScreen() = provider.provideRouter().newRootScreen(ShowMainScreenFragment)
}