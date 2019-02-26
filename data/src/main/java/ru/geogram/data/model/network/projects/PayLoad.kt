package ru.geogram.data.model.network.projects

data class PayLoad(
        val project_id:Int,
        val minutes_spent:Int,
        val date:String,
        val description:String
)