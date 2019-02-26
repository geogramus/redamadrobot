package ru.geogram.data.model.network.projects

import com.google.gson.annotations.SerializedName

data class PayLoad(
        @SerializedName("project_id")
        val project_id:Int,
        @SerializedName("minutes_spent")
        val minutes_spent:Int,
        @SerializedName("date")
        val date:String,
        @SerializedName("description")
        val description:String
)