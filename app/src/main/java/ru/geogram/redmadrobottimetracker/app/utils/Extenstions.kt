package ru.geogram.redmadrobottimetracker.app.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.gc.materialdesign.widgets.SnackBar
import ru.geogram.domain.model.days.WeekDates
import java.util.*
import java.text.SimpleDateFormat




fun parseServerError(code: String, description: String): String {
    var errorString = ""
    when (code) {
        "400" -> errorString = "Ошибка валидации\n${description}"
        "401" -> errorString = "Неправильный email или пароль\n${description}"
        else -> errorString = "Что то пошло не так"
    }
    return errorString
}

fun Fragment.showSnackBar(activity: Context, text: String, buttonText: String) {
    val snack = SnackBar((activity as Activity?), text, buttonText, View.OnClickListener {
    })
    snack.show()
}

fun View.Visible() {
    visibility = View.VISIBLE
}

fun View.InVisible() {
    visibility = View.GONE
}

@SuppressLint("SimpleDateFormat")
fun getCurrentWeekDate(date: Date? = null): WeekDates {
    val cal = Calendar.getInstance()

//    cal.set(2019, 2-1, 21)

    // "calculate" the start date of the week
    val first = cal.clone() as Calendar
    first.add(Calendar.DAY_OF_WEEK, first.firstDayOfWeek
    - first.get(Calendar.DAY_OF_WEEK))
    val last = first.clone() as Calendar
    last.add(Calendar.DAY_OF_YEAR, 6)
    val df = SimpleDateFormat("yyyy-MM-dd")

    return WeekDates(df.format(first.time), df.format(last.time))
}

private val DATE_FORMAT = "yyyy-MM-dd"
private val DAYS_IN_MONTH = 2
private fun nextMonth(): String {
    val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    return sdf.format(getDate(DAYS_IN_MONTH))
}

private fun previousMonth(): String {
    val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    return sdf.format(getDate(-DAYS_IN_MONTH))
}

private fun getDate(days: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, days)
    return calendar.time
}