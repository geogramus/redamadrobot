package ru.geogram.redmadrobottimetracker.app.di.module

import dagger.Module
import dagger.Provides
import ru.geogram.data.network.api.DaysApi
import ru.geogram.data.network.factory.AppApiFactory
import ru.geogram.data.repository.days.DaysDataRepository
import ru.geogram.domain.providers.dataProviders.TokenProvider
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
        internal fun provideDaysApi(appApiFactory: AppApiFactory) = appApiFactory.create(DaysApi::class.java)


        @JvmStatic
        @Provides
        @DaysScope
        internal fun provideDaysRepository(
            systemInfoProvider: SystemInfoProvider,
            userApi: DaysApi,
            tokenProvider: TokenProvider
        ): DaysRepository{
            return DaysDataRepository(systemInfoProvider, userApi, tokenProvider)
        }
    }
}