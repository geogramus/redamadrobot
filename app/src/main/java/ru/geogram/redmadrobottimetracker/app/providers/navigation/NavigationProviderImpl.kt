package ru.geogram.redmadrobottimetracker.app.providers.navigation

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router


class NavigationProviderImpl : NavigationHolderProvider, RouterProvider{
    lateinit var cicerone: Cicerone<Router>

    init {
        create()
    }

    fun create() {
        cicerone = Cicerone.create()
    }

    override fun provideRouter(): Router {
        return cicerone.getRouter()
    }

    override fun provideNavigationHolder(): NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }
}