package ru.geogram.redmadrobottimetracker.app.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.arch.lifecycle.LiveData
import ru.geogram.domain.model.user.LoginPassword
import ru.geogram.domain.repositories.AuthRepository
import ru.geogram.redmadrobottimetracker.app.utils.parseServerError
import javax.inject.Inject


class FragmentAuthoriztionViewModel @Inject constructor(private val authService: AuthRepository): BaseViewModel() {

    var liveData: MutableLiveData<String>? = MutableLiveData<String>()


    fun getData(): LiveData<String> {
        if (liveData == null) {
            liveData = MutableLiveData<String>()
        }
        return liveData!!
    }

    fun auth(model: LoginPassword) {
        val disposable = authService.auth(model)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
//                it.data?.let {
                    liveData?.postValue(it.first_name)
//                }
//                it.error?.let {
//                    liveData?.postValue(parseServerError(it.code, it.description))
//                }
            }, {
                liveData?.postValue("Что то пошло не так...")
                it.printStackTrace()
            })
        safeSubscribe { disposable }
    }
}