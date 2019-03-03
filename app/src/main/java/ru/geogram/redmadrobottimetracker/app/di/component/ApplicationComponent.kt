package ru.geogram.redmadrobottimetracker.app.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.geogram.redmadrobottimetracker.app.di.module.ApplicationModule
import ru.geogram.redmadrobottimetracker.app.di.scope.ApplicationScope
import ru.geogram.redmadrobottimetracker.app.presentation.activity.MainActivity
import ru.geogram.redmadrobottimetracker.app.presentation.fragments.DaysTasksFragment
import ru.geogram.redmadrobottimetracker.app.presentation.fragments.ProjectsFragment

@ApplicationScope
@Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {

    fun authComponent(): AuthComponent.Builder
    fun daysComponent(): DaysComponent.Builder
    fun projectsComponent(): ProjectsComponent.Builder
    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(appContext: Context): Builder

        fun build(): ApplicationComponent
    }
}