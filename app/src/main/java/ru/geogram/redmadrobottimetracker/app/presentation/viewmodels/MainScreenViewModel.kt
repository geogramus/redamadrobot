package ru.geogram.redmadrobottimetracker.app.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.geogram.domain.model.auth.ErrorInfo
import ru.geogram.domain.repositories.DaysRepository
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Data
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ErrorViewState
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Loading
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ViewState
import ru.geogram.redmadrobottimetracker.app.providers.navigation.RouterProvider
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    private val daysService: DaysRepository,
    private val provider: RouterProvider
) : BaseViewModel() {
    val days: MutableLiveData<ViewState> = MutableLiveData()

    init {
        loadDays()
    }

    fun loadDays() {
        days.postValue(Loading)
        val disposable = daysService
            .getDays("2019-02-14", "2019-02-21")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                days.postValue(Data(days = it))
            },
                {
                    days.postValue(
                        ErrorViewState(it)
                    )
                    it.printStackTrace()
                })
        safeSubscribe { disposable }
    }

}