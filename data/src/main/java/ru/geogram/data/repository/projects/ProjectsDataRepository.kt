package ru.geogram.data.repository.projects

import io.reactivex.Single
import ru.geogram.data.Utils
import ru.geogram.data.dao.projects.ProjectsEntityDao
import ru.geogram.data.model.converter.ProjectsConverter
import ru.geogram.data.model.db.projects.Project
import ru.geogram.data.model.network.projects.PayLoad
import ru.geogram.data.model.network.projects.PayloadResponse
import ru.geogram.data.model.network.projects.Projects
import ru.geogram.data.network.api.ProjectsApi
import ru.geogram.domain.model.projects.PayloadInfo
import ru.geogram.domain.model.projects.PayloadSucces
import ru.geogram.domain.model.projects.ProjectInf
import ru.geogram.domain.providers.resources.ResourceManagerProvider
import ru.geogram.domain.providers.system.SystemInfoProvider
import ru.geogram.domain.repositories.ProjectsRepository

class ProjectsDataRepository(
    private val systemInfoProvider: SystemInfoProvider,
    private val projectsApi: ProjectsApi,
    private val resourceManager: ResourceManagerProvider,
    private val projectsDao: ProjectsEntityDao
) : ProjectsRepository {

    override fun getProjects(recent: Boolean): Single<ArrayList<ProjectInf>> {
        val cookie = resourceManager.getToken()
        val networkObservable =
            getProjectsInfo(cookie, recent)
                .map(this::processResponse)
        var cacheIsValid = false
        val projectsDatabase = projectsDao.getProjects()
            .map {
                cacheIsValid = Utils.cacheIsValid(it.date)
                convertProjectsFromDb(it.projects)
            }

        if (!systemInfoProvider.hasNetwork()) {
            return projectsDatabase

        } else {
            return if (cacheIsValid) projectsDatabase
            else networkObservable
                .doOnSuccess(this::updateDb)
        }
    }

    override fun payload(payloadInfo: PayloadInfo): Single<PayloadSucces> {
        val cookie = resourceManager.getToken()
        val payLoad =
            PayLoad(payloadInfo.project_id, payloadInfo.minutes_spent, payloadInfo.date, payloadInfo.description)
        val networkObservable =
            payLoadTime(cookie, payLoad)
                .map(this::payLoadResponse)

        return networkObservable
    }


    private fun getProjectsInfo(cookie: String, recent: Boolean) = projectsApi.getProjects(cookie, recent)
    private fun processResponse(response: Projects) = ProjectsConverter.fromNetwork(response).projectList
    private fun payLoadTime(cookie: String, payloadInfo: PayLoad) = projectsApi.payloadTime(cookie, payloadInfo)
    private fun payLoadResponse(response: PayloadResponse) = ProjectsConverter.payLoad(response)

    fun convertProjectsFromDb(projects: ArrayList<Project>?): ArrayList<ProjectInf> = ProjectsConverter.fromDatabase(projects)

    private fun updateDb(list: ArrayList<ProjectInf>) {
        projectsDao.deleteAll()
        projectsDao.insert(ProjectsConverter.toDatabase(list))
    }
}