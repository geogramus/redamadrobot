package ru.geogram.redmadrobottimetracker.app.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import ru.geogram.domain.repositories.AuthRepository
import ru.geogram.redmadrobottimetracker.app.utils.onNext
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(private val authService: AuthRepository) : BaseViewModel() {

    val authCheck: MutableLiveData<UserViewState> = MutableLiveData()

    init {
        authCheck()
    }


    fun authCheck() {
        val disposable = authService
                .authCheck()
                .toObservable()
                .map<UserViewState>(::Data)
                .startWith(Loading)
                .onErrorReturn(::Error)
                .subscribe(authCheck::onNext) {
                    authCheck.postValue(Error(it))
                    it.printStackTrace()
                }
        safeSubscribe { disposable }
    }
}