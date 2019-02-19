package ru.geogram.redmadrobottimetracker.app.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.arch.lifecycle.LiveData
import ru.geogram.domain.model.auth.ErrorInfo
import ru.geogram.domain.model.auth.LoginPassword
import ru.geogram.domain.model.auth.UserInfo
import ru.geogram.domain.repositories.AuthRepository
import ru.geogram.redmadrobottimetracker.app.utils.parseServerError
import javax.inject.Inject


class AuthoriztionViewModel @Inject constructor(private val authService: AuthRepository) : BaseViewModel() {

    var userInfo: MutableLiveData<UserInfo>? = MutableLiveData<UserInfo>()
    var errorInfo: MutableLiveData<String>? = MutableLiveData<String>()

    fun getData(): LiveData<UserInfo> {
        if (userInfo == null) {
            userInfo = MutableLiveData<UserInfo>()
        }
        return userInfo!!
    }

    fun getError(): LiveData<String> {
        if (errorInfo == null) {
            errorInfo = MutableLiveData<String>()
        }
        return errorInfo!!
    }

    fun auth(model: LoginPassword) {
        val disposable = authService.auth(model)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.userInfo?.let {
                    userInfo?.postValue(it)
                }
                it.errorInfo?.let {
                    errorInfo?.postValue(
                        parseServerError(
                            it.code,
                            it.description
                        )
                    )
                }

            }, {
                errorInfo?.postValue("Что то пошло не так...")
                it.printStackTrace()
            })
        safeSubscribe { disposable }
    }
}