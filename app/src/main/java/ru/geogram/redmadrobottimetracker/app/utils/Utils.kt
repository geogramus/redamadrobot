package ru.geogram.redmadrobottimetracker.app.utils

import android.annotation.SuppressLint
import ru.geogram.domain.model.days.DayAndMonthModel
import ru.geogram.domain.model.days.ProjectInfoForDays
import ru.geogram.domain.model.days.WeekDates
import ru.geogram.redmadrobottimetracker.app.R
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
object Utils {
    private val DATE_FORMAT_PATTERN = "yyyy-MM-dd"
    private val MINUTES_IN_HOUR = 60

    fun getHours(projectsInfoForDays: ArrayList<ProjectInfoForDays>): String {
        var time = 0
        projectsInfoForDays.forEach {
            time += it.minutes_spent
        }
        return if (time == 0) {
            ""
        } else {
            "${(time / MINUTES_IN_HOUR)}"
        }
    }

    fun getHoursForSingleDay(time: Int): String {
        return if (time == 0) {
            ""
        } else {
            "${(time / MINUTES_IN_HOUR)}ч"
        }
    }

    fun getCurrentWeekDate(week: Int): WeekDates {
        val finalWeek = week
        val calendar = getCalendar(finalWeek, Calendar.MONDAY)
        val sdf = SimpleDateFormat(DATE_FORMAT_PATTERN)
        val from = sdf.format(calendar.time)
        val calendatTo = getCalendar(finalWeek, Calendar.SUNDAY)
        val to = sdf.format(calendatTo.time)
        return WeekDates(from, to)
    }

    private fun getCalendar(finalWeek: Int, weekDay: Int): Calendar {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.WEEK_OF_YEAR, finalWeek)
        calendar.set(Calendar.DAY_OF_WEEK, weekDay)
        calendar.add(Calendar.DAY_OF_WEEK, 1)
        return calendar
    }

    fun getDateAndMonth(week: Int): DayAndMonthModel {
        val finalWeek = week
        val calendarFrom = getCalendar(finalWeek, Calendar.MONDAY)
        val calendarTo = getCalendar(finalWeek, Calendar.SUNDAY)
        val monthFrom = calendarFrom.get(Calendar.MONTH)
        val monthTo = calendarTo.get(Calendar.MONTH)
        return if (monthFrom == monthTo) {
            DayAndMonthModel(calendarFrom.get(Calendar.DAY_OF_MONTH), calendarTo.get(Calendar.DAY_OF_MONTH),
                    getMonthName(monthTo))
        } else {
            DayAndMonthModel(calendarFrom.get(Calendar.DAY_OF_MONTH), calendarTo.get(Calendar.DAY_OF_MONTH),
                    getMonthName(monthTo), getMonthName(monthFrom))
        }
    }

    private fun getRightWeek(week: Int): Int {
        return week - 1
    }

    fun getDayOfWeek(date: String): Int {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat(DATE_FORMAT_PATTERN)
        calendar.setTime(sdf.parse(date))
        return getDayOfWeekResource(calendar.get(Calendar.DAY_OF_WEEK))
    }

    fun getDayMonth(date: String): String {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat(DATE_FORMAT_PATTERN)
        calendar.setTime(sdf.parse(date))
        val dayOfMonth = if (calendar.get(Calendar.DAY_OF_MONTH).toString().length == 1) "0${calendar.get(Calendar.DAY_OF_MONTH)}"
        else "${calendar.get(Calendar.DAY_OF_MONTH)}"
        val month = if ((calendar.get(Calendar.MONTH) + 1).toString().length == 1) "0${(calendar.get(Calendar.MONTH) + 1)}"
        else "${(calendar.get(Calendar.MONTH) + 1)}"
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

    fun isValidEmail(target: String): Boolean = android.util.Patterns.EMAIL_ADDRESS
            .matcher(target).matches()

}