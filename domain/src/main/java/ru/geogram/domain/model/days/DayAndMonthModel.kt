package ru.geogram.domain.model.days

data class DayAndMonthModel(
        val startDate: Int,
        val finishDate: Int,
        val finishMonth: Int,
        val startMonth: Int? = null
)