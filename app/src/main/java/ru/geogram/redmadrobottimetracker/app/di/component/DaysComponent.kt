package ru.geogram.redmadrobottimetracker.app.di.component

import dagger.Subcomponent
import ru.geogram.redmadrobottimetracker.app.di.module.DaysModule
import ru.geogram.redmadrobottimetracker.app.di.scope.DaysScope
import ru.geogram.redmadrobottimetracker.app.presentation.fragments.DaysTasksFragment
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.MainScreenViewModel

@DaysScope
@Subcomponent(modules = [DaysModule::class])
interface DaysComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): DaysComponent
    }

    fun inject(daysTasksFragment: DaysTasksFragment)
    fun daysTasksFragmentViewModel(): MainScreenViewModel
}