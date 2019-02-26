package ru.geogram.domain.model.projects

data class PayloadInfo(
        val project_id: Int,
        val minutes_spent: Int,
        val date: String,
        val description: String
)