package ru.geogram.domain.repositories

import io.reactivex.Single
import ru.geogram.domain.model.projects.PayloadInfo
import ru.geogram.domain.model.projects.PayloadSucces
import ru.geogram.domain.model.projects.ProjectsInfo

interface ProjectsRepository {
    fun getProjects(recent: Boolean): Single<ProjectsInfo>
    fun payload(payloadInfo: PayloadInfo): Single<PayloadSucces>
}