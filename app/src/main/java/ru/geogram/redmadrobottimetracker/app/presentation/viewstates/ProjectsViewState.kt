package ru.geogram.redmadrobottimetracker.app.presentation.viewstates

import ru.geogram.domain.model.projects.ProjectInf

sealed class ProjectsViewState
data class DataProjects(val projects: ArrayList<ProjectInf>) : ProjectsViewState()
object LoadingProjects : ProjectsViewState()
data class ErrorViewStateProjects(val error: Throwable) : ProjectsViewState()