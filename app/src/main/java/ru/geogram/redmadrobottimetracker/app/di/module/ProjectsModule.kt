package ru.geogram.redmadrobottimetracker.app.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.geogram.data.delegate.provideDataAppDatabase
import ru.geogram.data.network.api.ProjectsApi
import ru.geogram.data.network.factory.AppApiFactory
import ru.geogram.data.repository.projects.ProjectsDataRepository
import ru.geogram.data.storage.db.AppDatabase
import ru.geogram.domain.providers.dataProviders.TokenProvider
import ru.geogram.domain.providers.system.SystemInfoProvider
import ru.geogram.domain.repositories.ProjectsRepository
import ru.geogram.redmadrobottimetracker.app.di.scope.DaysScope

@Module
abstract class ProjectsModule {

    @Module
    companion object {


        @JvmStatic
        @Provides
        @DaysScope
        internal fun provideProjectsApi(appApiFactory: AppApiFactory) = appApiFactory.create(ProjectsApi::class.java)

        @JvmStatic
        @Provides
        @DaysScope
        internal fun provideAppDatabase(context: Context) = provideDataAppDatabase(context)


        @JvmStatic
        @Provides
        @DaysScope
        internal fun provideProjectsRepository(
            systemInfoProvider: SystemInfoProvider,
            projectsApi: ProjectsApi,
            tokenProvider: TokenProvider,
            appDatabase: AppDatabase
        ): ProjectsRepository {
            return ProjectsDataRepository(
                systemInfoProvider,
                projectsApi,
                tokenProvider,
                appDatabase.projectsEntityDao()
            )
        }
    }
}