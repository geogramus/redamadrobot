package ru.geogram.data

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    private val DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS"
    private const val MILLISECONDS_IN_HOUR = 60 * 60 * 1000F
    private val HOURS_IN_DAY = 24
    fun cacheIsValid(string: String?): Boolean {
        string?.let {
            val calendar = Calendar.getInstance()
            val sdf = SimpleDateFormat(DATE_FORMAT_PATTERN)
            calendar.setTime(sdf.parse(string))
            val diff = Date().time - calendar.time.time
            return diff / MILLISECONDS_IN_HOUR < HOURS_IN_DAY
        }
        return false
    }

    fun getCurrentDate(): String = SimpleDateFormat(DATE_FORMAT_PATTERN).format(Date())

}