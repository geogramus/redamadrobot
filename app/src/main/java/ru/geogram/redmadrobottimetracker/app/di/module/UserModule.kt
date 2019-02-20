package ru.geogram.redmadrobottimetracker.app.di.module

import dagger.Module
import dagger.Provides
import ru.geogram.data.network.api.AuthApi
import ru.geogram.data.network.factory.AppApiFactory
import ru.geogram.data.repository.auth.AuthDataRepository
import ru.geogram.data.storage.db.UserDatabaseInterface
import ru.geogram.domain.providers.resources.ResourceManagerProvider
import ru.geogram.domain.providers.rx.SchedulersProvider
import ru.geogram.domain.providers.system.SystemInfoProvider
import ru.geogram.domain.repositories.AuthRepository
import ru.geogram.redmadrobottimetracker.app.di.scope.UserScope

@Module
abstract class UserModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @UserScope
        internal fun provideAppApi(resourceManager: ResourceManagerProvider) = AppApiFactory(resourceManager)

        @JvmStatic
        @Provides
        @UserScope
        internal fun provideAuthApi(appApiFactory: AppApiFactory) = appApiFactory.create(AuthApi::class.java)


        @JvmStatic
        @Provides
        @UserScope
        internal fun provideUserRepository(
            schedulers: SchedulersProvider,
            systemInfoProvider: SystemInfoProvider,
            userApi: AuthApi,
            dataBase: UserDatabaseInterface,
            resourceManager: ResourceManagerProvider
        ): AuthRepository{
            return AuthDataRepository(schedulers, systemInfoProvider, userApi, dataBase, resourceManager)
        }
    }
}