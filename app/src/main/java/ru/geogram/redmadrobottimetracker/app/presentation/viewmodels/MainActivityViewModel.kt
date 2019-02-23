package ru.geogram.redmadrobottimetracker.app.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.geogram.domain.repositories.AuthRepository
import ru.geogram.redmadrobottimetracker.app.utils.onNext
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(private val authService: AuthRepository) : BaseViewModel() {

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
                authCheck.postValue(Data(it))
            },
                {
                    authCheck.postValue(Error(it))
                    it.printStackTrace()
                })
        safeSubscribe { disposable }
    }
}