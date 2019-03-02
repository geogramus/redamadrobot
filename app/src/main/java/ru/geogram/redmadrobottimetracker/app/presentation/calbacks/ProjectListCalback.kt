package ru.geogram.redmadrobottimetracker.app.presentation.calbacks

import ru.geogram.domain.model.projects.ProjectInf

interface ProjectListCalback {
    fun onProjectClick(project: ProjectInf)
}