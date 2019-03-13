package ru.geogram.redmadrobottimetracker.app.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import ru.geogram.domain.model.auth.LoginPassword
import ru.geogram.domain.providers.dataProviders.LoginPasswordProvider
import ru.geogram.domain.repositories.AuthRepository
import ru.geogram.redmadrobottimetracker.app.presentation.ShowMainScreenFragment
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ErrorViewState
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Loading
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ViewState
import ru.geogram.redmadrobottimetracker.app.providers.navigation.RouterProvider
import ru.geogram.redmadrobottimetracker.app.utils.schedulersToMain
import javax.inject.Inject


class AuthoriztionViewModel @Inject constructor(
        private val authService: AuthRepository,
        private val provider: RouterProvider,
        private val loginPasswordProvider: LoginPasswordProvider
) : BaseViewModel() {
    val router by lazy {
        provider.provideRouter()
    }
    val auth: MutableLiveData<ViewState> = MutableLiveData()
    val correctEmail: MutableLiveData<Boolean> = MutableLiveData()

    fun auth(model: LoginPassword) {
        auth.postValue(Loading)
        val disposable = authService
                .auth(model)
                .schedulersToMain()
                .subscribe(
                        {
                            router.newRootScreen(ShowMainScreenFragment)
                            loginPasswordProvider.setLoginPassword(model)
                        },
                        {
                            auth.postValue(
                                    ErrorViewState(it)
                            )
                            it.printStackTrace()
                        })

        safeSubscribe { disposable }
    }

    fun isValidEmail(target: String) {
        if (target.isEmpty()) {
            correctEmail.postValue(false)
        } else {
            correctEmail.postValue(android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches())
        }
    }
}