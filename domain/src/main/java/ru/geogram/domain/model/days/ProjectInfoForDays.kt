package ru.geogram.domain.model.days

data class ProjectInfoForDays (
    val projectId:Int,
    val id:Int,
    val name:String,
    val description:String,
    val minutes_spent: Int
)