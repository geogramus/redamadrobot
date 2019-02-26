package ru.geogram.data.model.converter

import ru.geogram.data.model.network.daysresponse.DaysResponse
import ru.geogram.domain.model.days.DaysInfo
import ru.geogram.domain.model.days.ProjectInfoForDays
import ru.geogram.domain.model.days.SingleDayInfo

object DaysConverter {
    fun fromNetwork(response: DaysResponse): DaysInfo{
        val days:ArrayList<SingleDayInfo> = ArrayList()

        response.data.days.forEach {
            val projectForDays:ArrayList<ProjectInfoForDays> = ArrayList()
            it.logged_time_records.forEach {
                val project = ProjectInfoForDays(it.project_id, it.project.id,
                    it.project.name, it.description, it.minutes_spent)
                projectForDays.add(project)
            }
            val day = SingleDayInfo(it.date, it.is_working, projectForDays)
            days.add(day)
        }
        return DaysInfo(days)
    }
}