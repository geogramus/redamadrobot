package ru.geogram.data.model.converter

import ru.geogram.data.Utils
import ru.geogram.data.model.db.projects.Project
import ru.geogram.data.model.db.projects.ProjectsEntity
import ru.geogram.data.model.network.projects.PayloadResponse
import ru.geogram.data.model.network.projects.Projects
import ru.geogram.domain.model.projects.PayloadSucces
import ru.geogram.domain.model.projects.ProjectInf
import ru.geogram.domain.model.projects.ProjectsInfo

object ProjectsConverter {
    fun fromNetwork(projects: Projects): ProjectsInfo {
        var projectsList = listOf<ProjectInf>()
        projects.data?.let {
            projectsList = it.projects.map {
                ProjectInf(it.id, it.name)
            }.toList()
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

    fun fromDatabase(projects: List<Project>?): List<ProjectInf> {
        return projects?.let {
            it.map {
                ProjectInf(it.id, it.name)
            }
        } ?: listOf<ProjectInf>()
    }

    fun toDatabase(list: List<ProjectInf>): ProjectsEntity {
        return ProjectsEntity(
            Utils.getCurrentDate(),
            list.map { Project(it.id, it.name) })
    }
}