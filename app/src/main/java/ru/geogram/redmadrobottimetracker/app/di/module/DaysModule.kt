package ru.geogram.redmadrobottimetracker.app.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.geogram.data.network.api.DaysApi
import ru.geogram.data.network.factory.AppApiFactory
import ru.geogram.data.repository.days.DaysDataRepository
import ru.geogram.data.threats.ThreatVerificationProviderImpl
import ru.geogram.domain.providers.dataProviders.TokenProvider
import ru.geogram.domain.providers.threats.ThreatVerificationProvider
import ru.geogram.domain.repositories.DaysRepository
import ru.geogram.redmadrobottimetracker.app.di.scope.DaysScope

@Module
abstract class DaysModule {

    @Module
    companion object {

        @JvmStatic
        @Provides
        @DaysScope
        internal fun provideDaysApi(appApiFactory: AppApiFactory) =
            appApiFactory.create(DaysApi::class.java)

        @JvmStatic
        @Provides
        @DaysScope
        internal fun provideThreats(context: Context): ThreatVerificationProvider =
            ThreatVerificationProviderImpl(context)

        @JvmStatic
        @Provides
        @DaysScope
        internal fun provideDaysRepository(
            userApi: DaysApi,
            tokenProvider: TokenProvider
        ): DaysRepository {
            return DaysDataRepository(userApi, tokenProvider)
        }
    }
}