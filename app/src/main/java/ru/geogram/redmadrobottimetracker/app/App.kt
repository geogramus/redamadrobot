package ru.geogram.redmadrobottimetracker.app

import android.app.Application
import ru.geogram.redmadrobottimetracker.app.di.DI

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        DI.init(this)
    }
}