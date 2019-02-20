package ru.geogram.redmadrobottimetracker.app.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import ru.geogram.domain.repositories.AuthRepository
import ru.geogram.redmadrobottimetracker.app.utils.onNext
import javax.inject.Inject


class UserFragmentViewModel @Inject constructor(private val authService: AuthRepository) : BaseViewModel() {

    val userCheck: MutableLiveData<UserViewState> = MutableLiveData()

    init {
        userInfo()
    }

    fun userInfo() {
        val disposable = authService
                .getProfile()
                .toObservable()
                .map<UserViewState>(::Data)
                .startWith(Loading)
                .onErrorReturn(::Error)
                .subscribe(userCheck::onNext) {
                    userCheck.postValue(Error(it))
                    it.printStackTrace()
                }
        safeSubscribe { disposable }
    }
}