package ru.geogram.redmadrobottimetracker.app.di.component

import dagger.Subcomponent
import ru.geogram.redmadrobottimetracker.app.di.module.UserModule
import ru.geogram.redmadrobottimetracker.app.di.scope.UserScope
import ru.geogram.redmadrobottimetracker.app.presentation.presenter.AuthoriztionViewModel
import ru.geogram.redmadrobottimetracker.app.presentation.presenter.MainActivityViewModel
import ru.geogram.redmadrobottimetracker.app.presentation.presenter.UserFragmentViewModel

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