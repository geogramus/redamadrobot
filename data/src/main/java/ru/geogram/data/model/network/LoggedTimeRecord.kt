package ru.geogram.data.model.network

import com.google.gson.annotations.SerializedName
import ru.geogram.data.model.network.daysresponse.Comment

data class LoggedTimeRecord(
        @SerializedName("comments")
    val comments: List<Comment>,
        @SerializedName("created_at")
    val created_at: String,
        @SerializedName("date")
    val date: String,
        @SerializedName("description")
    val description: String,
        @SerializedName("minutes_spent")
    val minutes_spent: Int,
        @SerializedName("project")
    val project: Project,
        @SerializedName("project_id")
    val project_id: Int,
        @SerializedName("status")
    val status: String,
        @SerializedName("updated_at")
    val updated_at: String,
        @SerializedName("user_id")
    val user_id: Int
)