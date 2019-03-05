package ru.geogram.data.model.network

import com.google.gson.Gson
import okhttp3.ResponseBody
import ru.geogram.domain.exceptions.network.ConvertException
import java.lang.Exception
import java.text.ParseException

private const val UNKNOWN_ERROR = "something went wrong"
fun getErrorMessage(result: ResponseBody?): String = try {
    Gson().fromJson(result.toString(), ResponseError::class.java).description
} catch (e: ParseException) {
    UNKNOWN_ERROR
}
