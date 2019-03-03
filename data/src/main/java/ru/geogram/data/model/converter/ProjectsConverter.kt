package ru.geogram.data.model.converter

import ru.geogram.data.model.network.projects.PayloadResponse
import ru.geogram.data.model.network.projects.Projects
import ru.geogram.domain.model.projects.PayloadSucces
import ru.geogram.domain.model.projects.ProjectInf
import ru.geogram.domain.model.projects.ProjectsInfo

object ProjectsConverter {
    fun fromNetwork(projects: Projects): ProjectsInfo {
        val projectsList = ArrayList<ProjectInf>()
        projects.data?.let {
            it.projects.forEach {
                projectsList.add(ProjectInf(it.id, it.name))
            }
        }
        return ProjectsInfo(projectsList)
    }

    fun payLoad(response: PayloadResponse): PayloadSucces {
        var id = 0
        var projectName = ""
        response.dataForPayloadRequest?.let {
            id = it.logged_time.project_id
            projectName = it.logged_time.project.name
        }
        return PayloadSucces(id, projectName)
    }
}