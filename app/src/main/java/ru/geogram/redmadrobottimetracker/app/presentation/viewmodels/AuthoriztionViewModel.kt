package ru.geogram.redmadrobottimetracker.app.presentation.viewmodels

import ru.geogram.domain.model.auth.LoginPassword
import ru.geogram.domain.providers.resources.ResourceManagerProvider
import ru.geogram.domain.repositories.AuthRepository
import ru.geogram.redmadrobottimetracker.app.utils.onNext
import javax.inject.Inject
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.geogram.redmadrobottimetracker.app.presentation.Screens
import ru.geogram.redmadrobottimetracker.app.providers.navigation.RouterProvider


class AuthoriztionViewModel @Inject constructor(
    private val authService: AuthRepository,
    private val provider: RouterProvider
) : BaseViewModel() {
    val router by lazy {
        provider.provideRouter()
    }
    val user: MutableLiveData<UserViewState> = MutableLiveData()
    val correctEmail: MutableLiveData<Boolean> = MutableLiveData()

    fun auth(model: LoginPassword) {
        user.postValue(Loading)
        val disposable = authService
            .auth(model)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                router.newRootScreen(Screens.ShowUserFragment)
            },
                {
                    user.postValue(Error(it, authService.getProfileFromDatabase()))
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