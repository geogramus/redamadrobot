package ru.geogram.redmadrobottimetracker.app.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.geogram.redmadrobottimetracker.app.di.module.ApplicationModule
import ru.geogram.redmadrobottimetracker.app.di.scope.ApplicationScope

@ApplicationScope
@Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {

    fun userComponent(): UserComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(appContext: Context): Builder

        fun build(): ApplicationComponent
    }
}