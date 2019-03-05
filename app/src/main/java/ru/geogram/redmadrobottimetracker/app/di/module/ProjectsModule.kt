package ru.geogram.redmadrobottimetracker.app.di.module

import dagger.Module
import dagger.Provides
import ru.geogram.data.network.api.DaysApi
import ru.geogram.data.network.api.ProjectsApi
import ru.geogram.data.network.factory.AppApiFactory
import ru.geogram.data.repository.days.DaysDataRepository
import ru.geogram.data.repository.projects.ProjectsDataRepository
import ru.geogram.domain.providers.resources.ResourceManagerProvider
import ru.geogram.domain.providers.rx.SchedulersProvider
import ru.geogram.domain.providers.system.SystemInfoProvider
import ru.geogram.domain.repositories.DaysRepository
import ru.geogram.domain.repositories.ProjectsRepository
import ru.geogram.redmadrobottimetracker.app.di.scope.DaysScope
@Module
abstract class ProjectsModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @DaysScope
        internal fun provideAppApi(resourceManager: ResourceManagerProvider) = AppApiFactory(resourceManager)

        @JvmStatic
        @Provides
        @DaysScope
        internal fun provideAuthApi(appApiFactory: AppApiFactory) = appApiFactory.create(ProjectsApi::class.java)


        @JvmStatic
        @Provides
        @DaysScope
        internal fun provideProjectsRepository(
            schedulers: SchedulersProvider,
            systemInfoProvider: SystemInfoProvider,
            projectsApi: ProjectsApi,
//            dataBase: UserDatabaseInterface,
            resourceManager: ResourceManagerProvider
        ): ProjectsRepository{
            return ProjectsDataRepository(schedulers, systemInfoProvider, projectsApi, resourceManager)
        }
    }
}