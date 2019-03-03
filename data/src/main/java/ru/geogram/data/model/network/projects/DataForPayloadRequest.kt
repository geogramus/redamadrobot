package ru.geogram.data.model.network.projects

import com.google.gson.annotations.SerializedName
import ru.geogram.data.model.network.LoggedTimeRecord

class DataForPayloadRequest (
        @SerializedName("logged_time")
        val logged_time:LoggedTimeRecord
)