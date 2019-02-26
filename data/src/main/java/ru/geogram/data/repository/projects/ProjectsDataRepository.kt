package ru.geogram.data.repository.projects

import io.reactivex.Single
import ru.geogram.data.model.converter.DaysConverter
import ru.geogram.data.model.converter.ProjectsConverter
import ru.geogram.data.model.network.projects.Projects
import ru.geogram.data.network.api.ProjectsApi
import ru.geogram.domain.model.projects.PayloadInfo
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

    override fun payload(payloadInfo: PayloadInfo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun getProjectsInfo(cookie: String, recent:Boolean) = projectsApi.getProjects(cookie, recent)
    private fun processResponse(response: Projects) = ProjectsConverter.fromNetwork(response)

}