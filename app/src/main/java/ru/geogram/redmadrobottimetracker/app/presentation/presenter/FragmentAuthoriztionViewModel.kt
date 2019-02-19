package ru.geogram.redmadrobottimetracker.app.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.arch.lifecycle.LiveData
import ru.geogram.domain.useCases.AuthUseCaseInterface
import ru.geogram.domain.useCases.AutheUseCase
import ru.geogram.entity.entity.LoginModel
import ru.geogram.redmadrobottimetracker.app.utils.parseServerError


class FragmentAuthoriztionViewModel : BaseViewModel() {

    var liveData: MutableLiveData<String>? = MutableLiveData<String>()
    var authService: AuthUseCaseInterface = AutheUseCase()


    fun getData(): LiveData<String> {
        if (liveData == null) {
            liveData = MutableLiveData<String>()
        }
        return liveData!!
    }

    fun auth(model: LoginModel) {
        val disposable = authService.auth(model)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.data?.let {
                    liveData?.postValue(it.user.first_name)
                }
                it.error?.let {
                    liveData?.postValue(parseServerError(it.code, it.description))
                }
            }, {
                liveData?.postValue("Что то пошло не так...")
                it.printStackTrace()
            })
        safeSubscribe { disposable }
    }
}