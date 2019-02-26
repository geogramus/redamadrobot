package ru.geogram.data.model.network.projects

import com.google.gson.annotations.SerializedName
import ru.geogram.data.model.network.ResponseError

class PayloadResponse (
        @SerializedName("data")
        val dataForPayloadRequest: DataForPayloadRequest? = null,
        @SerializedName("error")
        val error: ResponseError? = null
)