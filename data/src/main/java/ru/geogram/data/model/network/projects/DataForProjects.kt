package ru.geogram.data.model.network.projects

import com.google.gson.annotations.SerializedName
import ru.geogram.data.model.network.Project

data class DataForProjects (
        @SerializedName("projects")
        val projects: List<Project>
)