package ru.geogram.data.model.network

import com.google.gson.Gson
import okhttp3.ResponseBody
import ru.geogram.domain.exceptions.network.ConvertException

fun getErrorMessage(result: ResponseBody?): String {
    val error = Gson().fromJson(result.toString(), ResponseError::class.java)
    return error.description ?: "something went wrong"
}