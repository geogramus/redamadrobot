package ru.geogram.redmadrobottimetracker.app.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import ru.geogram.domain.repositories.AuthRepository
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Data
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ErrorViewState
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ViewState
import ru.geogram.redmadrobottimetracker.app.utils.schedulersToMain
import javax.inject.Inject


class UserFragmentViewModel @Inject constructor(private val authService: AuthRepository) : BaseViewModel() {

    val check: MutableLiveData<ViewState> = MutableLiveData()

    init {
        userInfo()
    }

    fun userInfo() {
        safeSubscribe {
            authService
                .getProfile()
                .schedulersToMain()
                .subscribe(
                    {
                        check.postValue(Data(it))
                    },
                    {
                        check.postValue(
                            ErrorViewState(
                                it
                            )
                        )
                        it.printStackTrace()
                    })
        }
    }
}