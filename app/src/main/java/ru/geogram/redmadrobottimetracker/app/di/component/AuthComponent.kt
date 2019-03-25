package ru.geogram.redmadrobottimetracker.app.di.component

import dagger.Subcomponent
import ru.geogram.redmadrobottimetracker.app.di.module.AuthModule
import ru.geogram.redmadrobottimetracker.app.di.scope.AuthScope
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.*

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
    fun createPinViewModel(): CreatePinViewModel
    fun enterPinViewModel(): EnterPinViewModel
    fun registrationViewModel(): RegistrationViewModel
}