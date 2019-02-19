package ru.geogram.redmadrobottimetracker.app.utils

fun parseServerError(code: String, description: String): String {
    var errorString = ""
    when (code) {
        "400" -> errorString = "Ошибка валидации\n${description}"
        "401" -> errorString = "Неправильный email или пароль\n${description}"
        else -> errorString = "Что то пошло не так"
    }
    return errorString
}