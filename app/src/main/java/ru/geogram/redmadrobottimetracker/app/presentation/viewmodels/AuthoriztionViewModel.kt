package ru.geogram.redmadrobottimetracker.app.presentation.viewmodels

import android.arch.lifecycle.MutableLiveData
import ru.geogram.domain.model.auth.LoginPassword
import ru.geogram.domain.providers.resources.ResourceManagerProvider
import ru.geogram.domain.repositories.AuthRepository
import ru.geogram.redmadrobottimetracker.app.utils.onNext
import javax.inject.Inject
import android.text.TextUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class AuthoriztionViewModel @Inject constructor(
        private val authService: AuthRepository,
        private val resources: ResourceManagerProvider
) : BaseViewModel() {

    val user: MutableLiveData<UserViewState> = MutableLiveData()
    val correctEmail: MutableLiveData<Boolean> = MutableLiveData()

    fun auth(model: LoginPassword) {
        user.postValue(Loading)
        val disposable = authService
                .auth(model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    user.postValue(Data(it))
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