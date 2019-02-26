package ru.geogram.redmadrobottimetracker.app.di.component

import dagger.Subcomponent
import ru.geogram.redmadrobottimetracker.app.di.module.DaysModule
import ru.geogram.redmadrobottimetracker.app.di.module.ProjectsModule
import ru.geogram.redmadrobottimetracker.app.di.scope.DaysScope
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.ProjectsFragmentViewModel

@DaysScope
@Subcomponent(modules = [ProjectsModule::class])
interface ProjectsComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): ProjectsComponent
    }
    fun projectsFragmentViewModel(): ProjectsFragmentViewModel
}