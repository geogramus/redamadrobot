package ru.geogram.data.repository.projects

import io.reactivex.Single
import ru.geogram.data.Utils
import ru.geogram.data.dao.projects.ProjectsEntityDao
import ru.geogram.data.model.converter.ProjectsConverter
import ru.geogram.data.model.network.projects.PayLoad
import ru.geogram.data.network.api.ProjectsApi
import ru.geogram.domain.model.projects.PayloadInfo
import ru.geogram.domain.model.projects.PayloadSucces
import ru.geogram.domain.model.projects.ProjectInf
import ru.geogram.domain.providers.dataProviders.TokenProvider
import ru.geogram.domain.providers.system.SystemInfoProvider
import ru.geogram.domain.repositories.ProjectsRepository

class ProjectsDataRepository(
    private val systemInfoProvider: SystemInfoProvider,
    private val projectsApi: ProjectsApi,
    private val tokenProvider: TokenProvider,
    private val projectsDao: ProjectsEntityDao
) : ProjectsRepository {

    override fun getProjects(recent: Boolean): Single<List<ProjectInf>> {
        val cookie = tokenProvider.getToken()
        val networkObservable = projectsApi.getProjects(cookie, recent)
            .map {
                ProjectsConverter.fromNetwork(it).projectList
            }
        var cacheIsValid = false
        val projectsDatabase = projectsDao.getProjects()
            .map {
                cacheIsValid = Utils.cacheIsValid(it.date)
                ProjectsConverter.fromDatabase(it.projects)
            }

        return if (!systemInfoProvider.hasNetwork()) {
            projectsDatabase
        } else {
            getValidProjects(cacheIsValid, networkObservable, projectsDatabase)
        }
    }

    private fun getValidProjects(
        cacheIsValid: Boolean, networkObservable: Single<List<ProjectInf>>,
        projectsDatabase: Single<List<ProjectInf>>
    ): Single<List<ProjectInf>> {
        return if (cacheIsValid) {
            projectsDatabase
        } else {
            networkObservable
                .doOnSuccess(this::updateDb)
        }
    }

    override fun payload(payloadInfo: PayloadInfo): Single<PayloadSucces> {
        val cookie = tokenProvider.getToken()
        val payLoad = PayLoad(
            payloadInfo.project_id, payloadInfo.minutes_spent,
            payloadInfo.date, payloadInfo.description
        )
        val networkObservable = projectsApi.payloadTime(cookie, payLoad)
            .map {
                ProjectsConverter.payLoad(it)
            }

        return networkObservable
    }

    private fun updateDb(list: List<ProjectInf>) {
        projectsDao.deleteAll()
        projectsDao.insert(ProjectsConverter.toDatabase(list))
    }
}