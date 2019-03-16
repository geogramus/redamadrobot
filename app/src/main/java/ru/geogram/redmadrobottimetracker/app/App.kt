package ru.geogram.redmadrobottimetracker.app

import android.app.Application
import com.orhanobut.hawk.Hawk
import ru.geogram.redmadrobottimetracker.app.di.DI


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
        Hawk.init(applicationContext).build()
    }

    private fun initDI() {
        DI.init(this)
    }

}