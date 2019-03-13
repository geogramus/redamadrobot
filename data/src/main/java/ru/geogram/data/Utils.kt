package ru.geogram.data

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    private val DATE_FORMAT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS"
    private const val MILLISECONDS_IN_HOUR = 60 * 60 * 1000F
    private val HOURS_IN_DAY = 24
    fun cacheIsValid(value: String?): Boolean {
        return value?.let {
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat(DATE_FORMAT_PATTERN)
            calendar.setTime(dateFormat.parse(value))
            val diff = Date().time - calendar.time.time
            diff / MILLISECONDS_IN_HOUR < HOURS_IN_DAY
        } ?: false
    }


    fun getCurrentDate(): String = SimpleDateFormat(DATE_FORMAT_PATTERN).format(Date())

}