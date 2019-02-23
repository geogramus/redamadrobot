package ru.geogram.redmadrobottimetracker.app.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                userCheck.postValue(Data(it))
            },
                {
                    userCheck.postValue(Error(it, authService.getProfileFromDatabase()))
                    it.printStackTrace()
                })

        safeSubscribe { disposable }
    }
}