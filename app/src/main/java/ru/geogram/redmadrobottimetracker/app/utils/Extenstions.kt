package ru.geogram.redmadrobottimetracker.app.utils

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.view.View
import com.gc.materialdesign.widgets.SnackBar


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