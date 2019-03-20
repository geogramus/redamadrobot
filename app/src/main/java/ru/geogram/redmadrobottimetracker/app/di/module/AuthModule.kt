package ru.geogram.redmadrobottimetracker.app.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.geogram.data.dataProvidersImpl.FingerProviderImpl
import ru.geogram.data.dataProvidersImpl.UserCredentialsProviderImpl
import ru.geogram.data.dataProvidersImpl.PinProviderImpl
import ru.geogram.data.network.api.AuthApi
import ru.geogram.data.network.factory.AppApiFactory
import ru.geogram.data.repository.auth.AuthDataRepository
import ru.geogram.domain.providers.crypto.TinkProvider
import ru.geogram.domain.providers.dataProviders.FingerProvider
import ru.geogram.domain.providers.dataProviders.UserCredentialsProvider
import ru.geogram.domain.providers.dataProviders.PinProvider
import ru.geogram.domain.providers.dataProviders.TokenProvider
import ru.geogram.domain.providers.system.SystemInfoProvider
import ru.geogram.domain.repositories.AuthRepository
import ru.geogram.redmadrobottimetracker.app.di.scope.AuthScope
import ru.geogram.redmadrobottimetracker.app.providers.crypto.TinkProviderImpl

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
        internal fun provideLoginPasswordData(): UserCredentialsProvider = UserCredentialsProviderImpl()

        @JvmStatic
        @Provides
        @AuthScope
        internal fun provideTink(context: Context): TinkProvider = TinkProviderImpl(context)

        @JvmStatic
        @Provides
        @AuthScope
        internal fun providePin(): PinProvider = PinProviderImpl()

        @JvmStatic
        @Provides
        @AuthScope
        internal fun provideFingerUsing(): FingerProvider = FingerProviderImpl()

        @JvmStatic
        @Provides
        @AuthScope
        internal fun provideUserRepository(
            systemInfoProvider: SystemInfoProvider,
            userApi: AuthApi,
            tokenProvider: TokenProvider,
            userCredentialsProvider: UserCredentialsProvider
        ): AuthRepository {
            return AuthDataRepository(systemInfoProvider, userApi, tokenProvider, userCredentialsProvider)
        }
    }
}