package ru.geogram.redmadrobottimetracker.app.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.arch.lifecycle.LiveData
import android.util.Log
import io.reactivex.internal.util.HalfSerializer.onNext
import ru.geogram.domain.model.auth.ErrorInfo
import ru.geogram.domain.model.auth.LoginPassword
import ru.geogram.domain.model.auth.UserInfo
import ru.geogram.domain.providers.resources.ResourceManagerProvider
import ru.geogram.domain.providers.system.SystemInfoProvider
import ru.geogram.domain.repositories.AuthRepository
import ru.geogram.redmadrobottimetracker.app.di.DI.user
import ru.geogram.redmadrobottimetracker.app.providers.resources.ResourceManagerProviderImpl
import ru.geogram.redmadrobottimetracker.app.utils.onNext
import ru.geogram.redmadrobottimetracker.app.utils.parseServerError
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(private val authService: AuthRepository) : BaseViewModel() {

    val user: MutableLiveData<UserViewState> = MutableLiveData()


    fun check(model: LoginPassword) {
        val disposable = authService
                .auth(model)
                .toObservable()
                .map<UserViewState>(::Data)
                .startWith(Loading)
                .onErrorReturn(::Error)
                .subscribe(user::onNext) {
                    it.printStackTrace()
                }
        safeSubscribe { disposable }
    }
}