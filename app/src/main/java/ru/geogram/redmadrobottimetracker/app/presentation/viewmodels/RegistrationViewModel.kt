package ru.geogram.redmadrobottimetracker.app.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import ru.geogram.domain.model.auth.RegistraionInfo
import ru.geogram.domain.repositories.AuthRepository
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Data
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ErrorViewState
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Loading
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ViewState
import ru.geogram.redmadrobottimetracker.app.providers.navigation.RouterProvider
import ru.geogram.redmadrobottimetracker.app.utils.Utils
import ru.geogram.redmadrobottimetracker.app.utils.schedulersToMain
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
        private val authService: AuthRepository,
        private val provider: RouterProvider
) : BaseViewModel() {

    companion object {
        private const val MIN_PASSWORD_LENGTH = 8
    }

    val registrate: MutableLiveData<ViewState> = MutableLiveData()
    val allFieldsCorrect: MutableLiveData<Boolean> = MutableLiveData()

    fun allFieldsAreValid(firstName: String, lastName: String, email: String, password: String) {
        val allValid = !firstName.isEmpty() && !lastName.isEmpty() && Utils.isValidEmail(email) && !password.isEmpty()
                && password.length >= MIN_PASSWORD_LENGTH
        if (allValid) {
            allFieldsCorrect.postValue(allValid)
        }
    }

    fun registrate(firstName: String, lastName: String, email: String, password: String) {
        safeSubscribe {
            registrate.postValue(Loading)
            authService
                    .registrate(RegistraionInfo(firstName, lastName, email, password))
                    .schedulersToMain()
                    .subscribe(
                            {
                                registrate.postValue(Data(it))
                            },
                            {
                                registrate.postValue(
                                        ErrorViewState(it)
                                )
                                it.printStackTrace()
                            })
        }
    }

    fun exit() {
        provider.provideRouter().exit()
    }
}