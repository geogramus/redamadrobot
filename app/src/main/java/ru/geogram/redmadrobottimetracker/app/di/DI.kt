package ru.geogram.redmadrobottimetracker.app.di

import android.content.Context
import ru.geogram.redmadrobottimetracker.app.di.component.*

object DI {
    lateinit var app: ApplicationComponent

    val user: ComponentHolder<UserComponent> = componentHolder(
        constructor = { app.userComponent().build() },
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