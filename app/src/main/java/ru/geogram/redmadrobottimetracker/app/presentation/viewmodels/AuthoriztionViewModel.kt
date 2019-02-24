package ru.geogram.redmadrobottimetracker.app.presentation.viewmodels

import ru.geogram.domain.model.auth.LoginPassword
import ru.geogram.domain.repositories.AuthRepository
import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.geogram.redmadrobottimetracker.app.presentation.Screens
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ViewState
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ErrorViewState
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Loading
import ru.geogram.redmadrobottimetracker.app.providers.navigation.RouterProvider


class AuthoriztionViewModel @Inject constructor(
    private val authService: AuthRepository,
    private val provider: RouterProvider
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
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                router.newRootScreen(Screens.ShowMainScreenFragment)
            },
                {
                    auth.postValue(
                        ErrorViewState(
                            it,
                            authService.getProfileFromDatabase()
                        )
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