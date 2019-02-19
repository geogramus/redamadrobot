package ru.geogram.redmadrobottimetracker.app.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.geogram.data.network.api.AuthApi
import ru.geogram.data.network.factory.AppApiFactory
import ru.geogram.data.repository.user.UserDataRepository
import ru.geogram.data.storage.db.TimeTrackerBoxStore
import ru.geogram.data.storage.db.UserDatabaseInterface
import ru.geogram.domain.providers.rx.SchedulersProvider
import ru.geogram.domain.providers.system.SystemInfoProvider
import ru.geogram.domain.repositories.AuthRepository
import ru.geogram.redmadrobottimetracker.app.di.scope.UserScope

@Module
abstract class UserModule {

    @Module
    companion object {
//
//        @JvmStatic
//        @Provides
//        @UserScope
//        internal fun provideAppDatabase(context: Context) = TimeTrackerBoxStore.getBoxStore(context)

        @JvmStatic
        @Provides
        @UserScope
        internal fun provideAppApi() = AppApiFactory()

        @JvmStatic
        @Provides
        @UserScope
        internal fun provideUserApi(appApiFactory: AppApiFactory) = appApiFactory.create(AuthApi::class.java)


        @JvmStatic
        @Provides
        @UserScope
        internal fun provideUserRepository(
            schedulers: SchedulersProvider,
            systemInfoProvider: SystemInfoProvider,
            userApi: AuthApi,
            dataBase: UserDatabaseInterface
        ): AuthRepository{
            return UserDataRepository(schedulers, systemInfoProvider, userApi, dataBase)
        }
    }
}