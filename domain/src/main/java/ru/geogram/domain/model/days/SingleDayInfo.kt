package ru.geogram.domain.model.days

data class SingleDayInfo(
    val date: String,
    val isWorking:Boolean,
    val projectsInfoForDays: ArrayList<ProjectInfoForDays>
)