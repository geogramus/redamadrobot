package ru.geogram.redmadrobottimetracker.app.presentation.viewstates

import ru.geogram.domain.model.auth.AuthInfo
import ru.geogram.domain.model.days.DaysInfo
import ru.geogram.domain.model.projects.PayloadSucces
import ru.geogram.domain.model.projects.ProjectsInfo


sealed class ViewState
data class Data(val user: AuthInfo? = null, val days: DaysInfo? = null,
                val projects: ProjectsInfo? = null, val payloadSucces: PayloadSucces? = null) : ViewState()
object Loading : ViewState()
data class ErrorViewState(val th: Throwable, val user: AuthInfo? = null, val days: DaysInfo? = null) : ViewState()