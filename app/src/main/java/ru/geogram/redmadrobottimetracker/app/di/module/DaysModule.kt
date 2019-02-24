package ru.geogram.redmadrobottimetracker.app.di.module

import dagger.Module
import dagger.Provides
import ru.geogram.data.network.api.DaysApi
import ru.geogram.data.network.factory.AppApiFactory
import ru.geogram.data.repository.days.DaysDataRepository
import ru.geogram.domain.providers.resources.ResourceManagerProvider
import ru.geogram.domain.providers.rx.SchedulersProvider
import ru.geogram.domain.providers.system.SystemInfoProvider
import ru.geogram.domain.repositories.DaysRepository
import ru.geogram.redmadrobottimetracker.app.di.scope.DaysScope
@Module
abstract class DaysModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @DaysScope
        internal fun provideAppApi(resourceManager: ResourceManagerProvider) = AppApiFactory(resourceManager)

        @JvmStatic
        @Provides
        @DaysScope
        internal fun provideAuthApi(appApiFactory: AppApiFactory) = appApiFactory.create(DaysApi::class.java)


        @JvmStatic
        @Provides
        @DaysScope
        internal fun provideUserRepository(
            schedulers: SchedulersProvider,
            systemInfoProvider: SystemInfoProvider,
            userApi: DaysApi,
//            dataBase: UserDatabaseInterface,
            resourceManager: ResourceManagerProvider
        ): DaysRepository{
            return DaysDataRepository(schedulers, systemInfoProvider, userApi, resourceManager)
        }
    }
}