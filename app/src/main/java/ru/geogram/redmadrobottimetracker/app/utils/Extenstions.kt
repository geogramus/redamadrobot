package ru.geogram.redmadrobottimetracker.app.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.gc.materialdesign.widgets.SnackBar
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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


fun <T> applySchedulers(): SingleTransformer<T, T> {
    return SingleTransformer { single ->
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
