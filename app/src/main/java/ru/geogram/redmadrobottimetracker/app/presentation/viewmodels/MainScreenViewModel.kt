package ru.geogram.redmadrobottimetracker.app.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.geogram.domain.model.auth.ErrorInfo
import ru.geogram.domain.model.days.WeekDates
import ru.geogram.domain.repositories.DaysRepository
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Data
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ErrorViewState
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Loading
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ViewState
import ru.geogram.redmadrobottimetracker.app.providers.navigation.RouterProvider
import ru.geogram.redmadrobottimetracker.app.utils.getCurrentWeekDate
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    private val daysService: DaysRepository,
    private val provider: RouterProvider
) : BaseViewModel() {
    val days: MutableLiveData<ViewState> = MutableLiveData()

    init {
        loadDays(getCurrentWeekDate())
    }

    fun loadDays(date:WeekDates) {
        days.postValue(Loading)
        val disposable = daysService
            .getDays(date.from, date.to)
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