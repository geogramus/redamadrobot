package ru.geogram.redmadrobottimetracker.app.presentation.viewstates

import ru.geogram.domain.model.auth.AuthInfo
import ru.geogram.domain.model.days.DaysInfo


sealed class ViewState
data class Data(val user: AuthInfo? = null, val days: DaysInfo? = null) : ViewState()
object Loading : ViewState()
data class ErrorViewState(val th: Throwable, val user: AuthInfo? = null, val days: DaysInfo? = null) : ViewState()