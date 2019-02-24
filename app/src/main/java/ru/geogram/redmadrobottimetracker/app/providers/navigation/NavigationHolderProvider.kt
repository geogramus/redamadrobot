package ru.geogram.redmadrobottimetracker.app.providers.navigation

import ru.terrakok.cicerone.NavigatorHolder

interface NavigationHolderProvider{
    fun provideNavigationHolder(): NavigatorHolder
}