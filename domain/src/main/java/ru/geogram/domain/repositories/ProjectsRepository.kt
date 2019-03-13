package ru.geogram.domain.repositories

import io.reactivex.Single
import ru.geogram.domain.model.projects.PayloadInfo
import ru.geogram.domain.model.projects.PayloadSucces
import ru.geogram.domain.model.projects.ProjectInf

interface ProjectsRepository {
    fun getProjects(recent: Boolean): Single<List<ProjectInf>>
    fun payload(payloadInfo: PayloadInfo): Single<PayloadSucces>
}