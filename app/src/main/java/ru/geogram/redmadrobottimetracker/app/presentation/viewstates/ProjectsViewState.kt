package ru.geogram.redmadrobottimetracker.app.presentation.viewstates

import ru.geogram.domain.model.auth.AuthInfo
import ru.geogram.domain.model.days.DaysInfo
import ru.geogram.domain.model.projects.PayloadSucces
import ru.geogram.domain.model.projects.ProjectsInfo

sealed class ProjectsViewState
data class DataProjects( val projects: ProjectsInfo? = null) : ProjectsViewState()
object LoadingProjects : ProjectsViewState()
data class ErrorViewStateProjects(val th: Throwable) : ProjectsViewState()