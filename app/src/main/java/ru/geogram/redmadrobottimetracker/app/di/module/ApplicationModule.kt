package ru.geogram.redmadrobottimetracker.app.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.geogram.data.dataProvidersImpl.TokenProviderImpl
import ru.geogram.data.network.factory.AppApiFactory
import ru.geogram.domain.providers.dataProviders.TokenProvider
import ru.geogram.domain.providers.resources.ResourceManagerProvider
import ru.geogram.domain.providers.system.SystemInfoProvider
import ru.geogram.redmadrobottimetracker.app.di.scope.ApplicationScope
import ru.geogram.redmadrobottimetracker.app.providers.navigation.NavigationProviderImpl
import ru.geogram.redmadrobottimetracker.app.providers.navigation.RouterProvider
import ru.geogram.redmadrobottimetracker.app.providers.resources.ResourceManagerProviderImpl
import ru.geogram.redmadrobottimetracker.app.providers.system.SystemInfoDataProvider
import ru.terrakok.cicerone.NavigatorHolder

@Module
internal abstract class ApplicationModule {


    @Module
    companion object {


        @JvmStatic
        @Provides
        @ApplicationScope
        internal fun provideSystemInfoProvider(context: Context): SystemInfoProvider = SystemInfoDataProvider(context)


        @JvmStatic
        @Provides
        @ApplicationScope
        internal fun provideResourceManager(context: Context): ResourceManagerProvider =
            ResourceManagerProviderImpl(context)

        val navigator = NavigationProviderImpl()

        @JvmStatic
        @Provides
        @ApplicationScope
        internal fun provideNavigationHolder(): NavigatorHolder = navigator.provideNavigationHolder()

        @JvmStatic
        @Provides
        @ApplicationScope
        internal fun provideRouterProvider(): RouterProvider = navigator

        @JvmStatic
        @Provides
        @ApplicationScope
        internal fun provideToken(): TokenProvider = TokenProviderImpl()

        @JvmStatic
        @Provides
        @ApplicationScope
        internal fun provideAppApi(tokenProvider: TokenProvider) = AppApiFactory(tokenProvider)
    }
}