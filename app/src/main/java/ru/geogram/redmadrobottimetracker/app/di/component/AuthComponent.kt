package ru.geogram.redmadrobottimetracker.app.di.component

import dagger.Subcomponent
import ru.geogram.redmadrobottimetracker.app.di.module.AuthModule
import ru.geogram.redmadrobottimetracker.app.di.scope.AuthScope
import ru.geogram.redmadrobottimetracker.app.presentation.activity.MainActivity
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.AuthoriztionViewModel
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.MainActivityViewModel
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.UserFragmentViewModel
import ru.geogram.redmadrobottimetracker.app.providers.navigation.NavigationProviderImpl

@AuthScope
@Subcomponent(modules = [AuthModule::class])
interface AuthComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): AuthComponent
    }

    fun authViewModel(): AuthoriztionViewModel
    fun mainViewModel(): MainActivityViewModel
    fun userFragmentViewModel(): UserFragmentViewModel
}