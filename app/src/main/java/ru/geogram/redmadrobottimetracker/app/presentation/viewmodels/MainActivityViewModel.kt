package ru.geogram.redmadrobottimetracker.app.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import ru.geogram.domain.repositories.AuthRepository
import ru.geogram.redmadrobottimetracker.app.presentation.ShowAuthFragment
import ru.geogram.redmadrobottimetracker.app.presentation.ShowEnterPinFragment
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Loading
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ViewState
import ru.geogram.redmadrobottimetracker.app.providers.navigation.RouterProvider
import ru.geogram.redmadrobottimetracker.app.utils.schedulersToMain
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(
        private val authService: AuthRepository,
        private val provider: RouterProvider
) : BaseViewModel() {
    val router by lazy {
        provider.provideRouter()
    }
    val check: MutableLiveData<ViewState> = MutableLiveData()

    init {
        authCheck()
    }


    fun authCheck() {
        check.postValue(Loading)
        val disposable = authService
                .authCheck()
                .schedulersToMain()
                .subscribe(
                        {
                            router.newRootScreen(ShowEnterPinFragment)
                        },
                        {
                            router.newRootScreen(ShowAuthFragment)
                            it.printStackTrace()
                        })
        safeSubscribe { disposable }
    }
}