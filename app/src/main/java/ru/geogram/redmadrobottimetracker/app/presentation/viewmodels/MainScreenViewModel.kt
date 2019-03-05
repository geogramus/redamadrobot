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
import ru.geogram.redmadrobottimetracker.app.utils.Utils.getCurrentWeekDate
import ru.geogram.redmadrobottimetracker.app.utils.schedulersToMain
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    private val daysService: DaysRepository
) : BaseViewModel() {
    val days: MutableLiveData<ViewState> = MutableLiveData()
    private val defaultWeekNumber = 0

    init {
        loadDays(getCurrentWeekDate(defaultWeekNumber))
    }

    fun loadNewWeek(week: Int) {
        loadDays(getCurrentWeekDate(week))
    }

    fun loadDays(date: WeekDates) {
        onCleared()
        days.postValue(Loading)
        safeSubscribe {
            daysService
                .getDays(date.from, date.to)
                .schedulersToMain()
                .subscribe({
                    days.postValue(Data(days = it))
                },
                    {
                        days.postValue(
                            ErrorViewState(it)
                        )
                        it.printStackTrace()
                    })
        }
    }

}