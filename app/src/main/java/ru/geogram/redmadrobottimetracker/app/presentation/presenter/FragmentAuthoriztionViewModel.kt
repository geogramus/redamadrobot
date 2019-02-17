package ru.geogram.redmadrobottimetracker.app.presentation.presenter

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import android.R.attr.data
import android.arch.lifecycle.LiveData
import ru.geogram.domain.useCases.AuthUseCaseInterface
import ru.geogram.domain.useCases.AutheUseCase
import ru.geogram.entity.entity.LoginModel


class FragmentAuthoriztionViewModel : ViewModel() {

    var liveData: MutableLiveData<String>? = MutableLiveData<String>()
    var authService: AuthUseCaseInterface = AutheUseCase()


    val compositeDisposable by lazy {
        CompositeDisposable()
    }

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
                    liveData?.postValue(it.code)
                }
            }, {
                liveData?.postValue(it.message)
                it.printStackTrace()
            })
        compositeDisposable.add(disposable)
    }
}