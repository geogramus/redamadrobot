package ru.geogram.redmadrobottimetracker.app.utils

import android.annotation.SuppressLint
import ru.geogram.domain.model.days.DayAndMonthModel
import ru.geogram.domain.model.days.ProjectInfoForDays
import ru.geogram.domain.model.days.WeekDates
import ru.geogram.redmadrobottimetracker.app.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@SuppressLint("SimpleDateFormat")
object Utils {
    private val DATE_FORMAT_PATTERN = "yyyy-MM-dd"
    private val MINUTES_IN_HOUR = 60

    fun getHours(projectsInfoForDays: ArrayList<ProjectInfoForDays>): String {
        var time = 0
        projectsInfoForDays.forEach {
            time += it.minutes_spent
        }
        if (time == 0) {
            return ""
        } else {
            return "${(time / MINUTES_IN_HOUR)}"
        }
    }

    fun getHoursForSingleDay(time: Int): String {
        if (time == 0) {
            return ""
        } else {
            return "${(time / MINUTES_IN_HOUR)}ч"
        }
    }

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

    fun getDateAndMonth(week: Int): DayAndMonthModel {
        val finalWeek = week
        val calFrom = getCalendar(finalWeek, Calendar.MONDAY)
        val calTo = getCalendar(finalWeek, Calendar.SUNDAY)
        val monthFrom = calFrom.get(Calendar.MONTH)
        val monthTo = calTo.get(Calendar.MONTH)
        if (monthFrom == monthTo) {
            return DayAndMonthModel(calFrom.get(Calendar.DAY_OF_MONTH), calTo.get(Calendar.DAY_OF_MONTH),
                    getMonthName(monthTo))
        } else {
            return DayAndMonthModel(calFrom.get(Calendar.DAY_OF_MONTH), calTo.get(Calendar.DAY_OF_MONTH),
                    getMonthName(monthTo), getMonthName(monthFrom))
        }
    }

    private fun getRightWeek(week: Int): Int {
        return week - 1
    }

    fun getDayOfWeek(date: String): Int {
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat(DATE_FORMAT_PATTERN)
        cal.setTime(sdf.parse(date))
        return getDayOfWeekResource(cal.get(Calendar.DAY_OF_WEEK))
    }

    fun getDayMonth(date: String): String {
        val calen = Calendar.getInstance()
        val sdf = SimpleDateFormat(DATE_FORMAT_PATTERN)
        calen.setTime(sdf.parse(date))
        val dayOfMonth = if (calen.get(Calendar.DAY_OF_MONTH).toString().length == 1) "0${calen.get(Calendar.DAY_OF_MONTH)}"
        else "${calen.get(Calendar.DAY_OF_MONTH)}"
        val month = if ((calen.get(Calendar.MONTH) + 1).toString().length == 1) "0${(calen.get(Calendar.MONTH) + 1)}"
        else "${(calen.get(Calendar.MONTH) + 1)}"
        return "${dayOfMonth}.${month}"
    }

    private fun getDayOfWeekResource(day: Int): Int {
        return when (day) {
            Calendar.MONDAY -> {
                R.string.monday
            }
            Calendar.TUESDAY -> {
                R.string.tuesday
            }
            Calendar.WEDNESDAY -> {
                R.string.wednesday
            }
            Calendar.THURSDAY -> {
                R.string.thusday
            }
            Calendar.FRIDAY -> {
                R.string.friday
            }
            Calendar.SATURDAY -> {
                R.string.saturday
            }
            Calendar.SUNDAY -> {
                R.string.sunday
            }
            else -> {
                R.string.monday
            }

        }
    }

    private fun getMonthName(month: Int): Int {
        return when (month) {
            Calendar.JANUARY -> {
                R.string.january
            }
            Calendar.FEBRUARY -> {
                R.string.february
            }
            Calendar.MARCH -> {
                R.string.march
            }
            Calendar.APRIL -> {
                R.string.april
            }
            Calendar.MAY -> {
                R.string.may
            }
            Calendar.JUNE -> {
                R.string.juny
            }
            Calendar.JULY -> {
                R.string.july
            }
            Calendar.AUGUST -> {
                R.string.august
            }
            Calendar.SEPTEMBER -> {
                R.string.september
            }
            Calendar.OCTOBER -> {
                R.string.october
            }
            Calendar.NOVEMBER -> {
                R.string.november
            }
            Calendar.DECEMBER -> {
                R.string.december
            }
            else -> {
                R.string.january
            }

        }
    }


}