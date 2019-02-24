package ru.geogram.redmadrobottimetracker.app.providers.navigation

import ru.terrakok.cicerone.Router

interface RouterProvider {
    fun provideRouter(): Router
}