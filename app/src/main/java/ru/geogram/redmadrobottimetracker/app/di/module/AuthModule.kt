package ru.geogram.redmadrobottimetracker.app.di.module

import dagger.Module
import dagger.Provides
import ru.geogram.data.dataProvidersImpl.LoginPasswordProviderImpl
import ru.geogram.data.network.api.AuthApi
import ru.geogram.data.network.factory.AppApiFactory
import ru.geogram.data.repository.auth.AuthDataRepository
import ru.geogram.domain.providers.dataProviders.LoginPasswordProvider
import ru.geogram.domain.providers.dataProviders.TokenProvider
import ru.geogram.domain.providers.system.SystemInfoProvider
import ru.geogram.domain.repositories.AuthRepository
import ru.geogram.redmadrobottimetracker.app.di.scope.AuthScope

@Module
abstract class AuthModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @AuthScope
        internal fun provideAuthApi(appApiFactory: AppApiFactory) = appApiFactory.create(AuthApi::class.java)


        @JvmStatic
        @Provides
        @AuthScope
        internal fun provideLoginPasswordData():LoginPasswordProvider = LoginPasswordProviderImpl()

        @JvmStatic
        @Provides
        @AuthScope
        internal fun provideUserRepository(
            systemInfoProvider: SystemInfoProvider,
            userApi: AuthApi,
            tokenProvider: TokenProvider,
            loginPasswordProvider: LoginPasswordProvider
        ): AuthRepository{
            return AuthDataRepository(systemInfoProvider, userApi, tokenProvider, loginPasswordProvider)
        }
    }
}