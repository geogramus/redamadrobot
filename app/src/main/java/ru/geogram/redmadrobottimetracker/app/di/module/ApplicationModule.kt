package ru.geogram.redmadrobottimetracker.app.di.module

import android.content.Context

import dagger.Module
import dagger.Provides
import ru.geogram.data.storage.db.UserDatabase
import ru.geogram.data.storage.db.UserDatabaseInterface
import ru.geogram.domain.providers.rx.SchedulersProvider
import ru.geogram.domain.providers.system.SystemInfoProvider
import ru.geogram.redmadrobottimetracker.app.providers.rx.SchedulersProviderImpl
import ru.geogram.redmadrobottimetracker.app.providers.system.SystemInfoDataProvider
import ru.geogram.redmadrobottimetracker.app.di.scope.ApplicationScope

@Module
internal abstract class ApplicationModule {


    @Module
    companion object {

        @JvmStatic
        @Provides
        @ApplicationScope
        internal fun provideSchedulersProvider(): SchedulersProvider = SchedulersProviderImpl()

        @JvmStatic
        @Provides
        @ApplicationScope
        internal fun provideSystemInfoProvider(context: Context): SystemInfoProvider = SystemInfoDataProvider(context)

        @JvmStatic
        @Provides
        @ApplicationScope
        internal fun provideUserDatabase(context: Context): UserDatabaseInterface = UserDatabase(context)
    }
}