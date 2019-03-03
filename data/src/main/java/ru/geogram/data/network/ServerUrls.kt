package ru.geogram.data.network

object ServerUrls {
    open class ServerUrl(val url: String)

    class RedMadRobot : ServerUrl("https://watcher.intern.redmadrobot.com/api/v1/")
}