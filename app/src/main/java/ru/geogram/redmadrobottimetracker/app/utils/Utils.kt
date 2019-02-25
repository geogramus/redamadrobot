package ru.geogram.redmadrobottimetracker.app.utils

import android.annotation.SuppressLint
import ru.geogram.domain.model.days.WeekDates
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
object Utils {
    private val DATE_FORMAT_PATTERN = "yyyy-MM-dd"

    fun getCurrentWeekDate(week: Int): WeekDates {
        val finalWeek = week
        val cal = getCalendar(finalWeek, Calendar.MONDAY)
        val sdf = SimpleDateFormat(DATE_FORMAT_PATTERN)
        val from = sdf.format(cal.time)
        val calTo = getCalendar(finalWeek, Calendar.SUNDAY)
        val to = sdf.format(calTo.time)
        return WeekDates(from, to)
    }

    private fun getCalendar(finalWeek: Int, weekDay: Int): Calendar {
        val cal = Calendar.getInstance()
        cal.add(Calendar.WEEK_OF_YEAR, finalWeek)
        cal.set(Calendar.DAY_OF_WEEK, weekDay)
        cal.add(Calendar.DAY_OF_WEEK, 1)
        return cal
    }

    fun getDateAndMonthString(week: Int): String {
        val finalWeek = week
        val calFrom = getCalendar(finalWeek, Calendar.MONDAY)
        val calTo = getCalendar(finalWeek, Calendar.SUNDAY)
        val monthFrom = calFrom.get(Calendar.MONTH)
        val monthTo = calFrom.get(Calendar.MONTH)
        if (monthFrom == monthTo) {
            return "${calFrom.get(Calendar.DAY_OF_MONTH)} - ${calTo.get(Calendar.DAY_OF_MONTH)} ${getMonthName(monthFrom)}"
        } else {
            return "${calFrom.get(Calendar.DAY_OF_MONTH)}${getMonthName(monthFrom)} - ${calTo.get(Calendar.DAY_OF_MONTH)} ${getMonthName(monthTo)}"
        }
    }

    private fun getRightWeek(week: Int): Int {
        return week - 1
    }

    private fun getMonthName(month: Int): String {
        return when (month) {
            Calendar.JANUARY -> {
                "Января"
            }
            Calendar.FEBRUARY -> {
                "Февраля"
            }
            Calendar.MARCH -> {
                "Марта"
            }
            Calendar.APRIL -> {
                "Апреля"
            }
            Calendar.MAY -> {
                "Мая"
            }
            Calendar.JUNE -> {
                "Июня"
            }
            Calendar.JULY -> {
                "Июля"
            }
            Calendar.AUGUST -> {
                "Августа"
            }
            Calendar.SEPTEMBER -> {
                "Сентября"
            }
            Calendar.OCTOBER -> {
                "Октября"
            }
            Calendar.NOVEMBER -> {
                "Ноября"
            }
            Calendar.DECEMBER -> {
                "Декабря"
            }
            else -> {
                "Января"
            }

        }
    }


}