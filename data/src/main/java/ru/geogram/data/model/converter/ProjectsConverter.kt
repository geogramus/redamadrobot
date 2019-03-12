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

    fun fromDatabase(projects: ArrayList<Project>?): ArrayList<ProjectInf> {
        val projectsList = ArrayList<ProjectInf>()
        projects?.let {
            it.forEach {
                projectsList.add(ProjectInf(it.id, it.name))
            }
        }
        return projectsList
    }

    fun toDatabase(list: ArrayList<ProjectInf>):ProjectsEntity{
        val projectsList = ArrayList<Project>()
        list.forEach {
            projectsList.add(Project(it.id, it.name))
        }
        return ProjectsEntity(Utils.getCurrentDate(), projectsList)
    }
}