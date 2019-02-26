package ru.geogram.data.model.network.projects

import com.google.gson.annotations.SerializedName
import ru.geogram.data.model.network.ResponseError

data class Projects(
        @SerializedName("data")
        val data: DataForProjects? = null,
        val error: ResponseError? = null
)