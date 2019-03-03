package ru.geogram.data.repository.projects

import io.reactivex.Single
import ru.geogram.data.model.converter.ProjectsConverter
import ru.geogram.data.model.network.projects.PayLoad
import ru.geogram.data.model.network.projects.PayloadResponse
import ru.geogram.data.model.network.projects.Projects
import ru.geogram.data.network.api.ProjectsApi
import ru.geogram.domain.model.projects.PayloadInfo
import ru.geogram.domain.model.projects.PayloadSucces
import ru.geogram.domain.model.projects.ProjectsInfo
import ru.geogram.domain.providers.resources.ResourceManagerProvider
import ru.geogram.domain.providers.rx.SchedulersProvider
import ru.geogram.domain.providers.system.SystemInfoProvider
import ru.geogram.domain.repositories.ProjectsRepository

class ProjectsDataRepository(
        val schedulers: SchedulersProvider,
        private val systemInfoProvider: SystemInfoProvider,
        private val projectsApi: ProjectsApi,
//private val boxStore: UserDatabaseInterface,
        private val resourceManager: ResourceManagerProvider
) : ProjectsRepository {

    override fun getProjects(recent: Boolean): Single<ProjectsInfo> {
        val cookie = resourceManager.getToken()
        val networkObservable =
                getProjectsInfo(cookie, recent)
                        .subscribeOn(schedulers.io())
                        .observeOn(schedulers.computation())
                        .map(this::processResponse)

        return networkObservable
    }

    override fun payload(payloadInfo: PayloadInfo): Single<PayloadSucces> {
        val cookie = resourceManager.getToken()
        val payLoad = PayLoad(payloadInfo.project_id, payloadInfo.minutes_spent, payloadInfo.date, payloadInfo.description)
        val networkObservable =
                payLoadTime(cookie, payLoad)
                        .subscribeOn(schedulers.io())
                        .observeOn(schedulers.computation())
                        .map(this::payLoadResponse)

        return networkObservable
    }


    private fun getProjectsInfo(cookie: String, recent: Boolean) = projectsApi.getProjects(cookie, recent)
    private fun processResponse(response: Projects) = ProjectsConverter.fromNetwork(response)
    private fun payLoadTime(cookie: String, payloadInfo: PayLoad) = projectsApi.payloadTime(cookie, payloadInfo)
    private fun payLoadResponse(response: PayloadResponse) = ProjectsConverter.payLoad(response)
}