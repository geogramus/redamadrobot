package ru.geogram.redmadrobottimetracker.app.di

import android.content.Context
import ru.geogram.redmadrobottimetracker.app.di.component.*

object DI {
    lateinit var app: ApplicationComponent

    val AUTH: ComponentHolder<AuthComponent> = componentHolder(
        constructor = { app.authComponent().build() },
        destructor = {
            // destroy child's components here
        }
    )

    val DAYS: ComponentHolder<DaysComponent> = componentHolder(
        constructor = { app.daysComponent().build() },
        destructor = {
            // destroy child's components here
        }
    )

    val PROJECTS: ComponentHolder<ProjectsComponent> = componentHolder(
            constructor = { app.projectsComponent().build() },
            destructor = {
                // destroy child's components here
            }
    )

    fun init(context: Context) {
        this.app = DaggerApplicationComponent
            .builder()
            .context(context)
            .build()
    }
}