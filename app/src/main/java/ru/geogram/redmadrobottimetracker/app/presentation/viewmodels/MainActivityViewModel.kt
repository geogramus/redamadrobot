package ru.geogram.redmadrobottimetracker.app.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.geogram.domain.repositories.AuthRepository
import ru.geogram.redmadrobottimetracker.app.presentation.Screens
import ru.geogram.redmadrobottimetracker.app.providers.navigation.RouterProvider
import ru.geogram.redmadrobottimetracker.app.utils.onNext
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(
    private val authService: AuthRepository,
    private val provider: RouterProvider
) : BaseViewModel() {
    val router by lazy {
        provider.provideRouter()
    }
    val authCheck: MutableLiveData<UserViewState> = MutableLiveData()

    init {
        authCheck()
    }


    fun authCheck() {
        authCheck.postValue(Loading)
        val disposable = authService
            .authCheck()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                router.newRootScreen(Screens.ShowUserFragment)
            },
                {
                    router.newRootScreen(Screens.ShowAuthFragment)
                    it.printStackTrace()
                })
        safeSubscribe { disposable }
    }
}