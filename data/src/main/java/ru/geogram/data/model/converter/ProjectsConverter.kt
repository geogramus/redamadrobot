package ru.geogram.data.model.converter

import ru.geogram.data.model.network.projects.Projects
import ru.geogram.domain.model.projects.ProjectInf
import ru.geogram.domain.model.projects.ProjectsInfo

object ProjectsConverter {
    fun fromNetwork(projects: Projects): ProjectsInfo{
        val projectsList = ArrayList<ProjectInf>()
        projects.data?.let {
            it.projects.forEach {
                projectsList.add(ProjectInf(it.id, it.name))
            }
        }
        return ProjectsInfo(projectsList)
    }
}