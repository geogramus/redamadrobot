package ru.geogram.redmadrobottimetracker.app.di.component

import dagger.Subcomponent
import ru.geogram.redmadrobottimetracker.app.di.module.UserModule
import ru.geogram.redmadrobottimetracker.app.di.scope.UserScope
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.AuthoriztionViewModel
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.MainActivityViewModel
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.UserFragmentViewModel

@UserScope
@Subcomponent(modules = [UserModule::class])
interface UserComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): UserComponent
    }

    fun authViewModel(): AuthoriztionViewModel
    fun mainViewModel(): MainActivityViewModel
    fun userFragmentViewModel(): UserFragmentViewModel
}