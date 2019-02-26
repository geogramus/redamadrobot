package ru.geogram.redmadrobottimetracker.app.di.component

import dagger.Subcomponent
import ru.geogram.redmadrobottimetracker.app.di.module.DaysModule
import ru.geogram.redmadrobottimetracker.app.di.scope.DaysScope
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.MainScreenViewModel
import ru.geogram.redmadrobottimetracker.app.providers.navigation.NavigationProviderImpl

@DaysScope
@Subcomponent(modules = [DaysModule::class])
interface DaysComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): DaysComponent
    }

    fun daysTasksFragmentViewModel(): MainScreenViewModel
}