package ru.geogram.data.model.converter

import ru.geogram.data.model.daysresponse.DataForDayResponse
import ru.geogram.data.model.daysresponse.DaysResponse
import ru.geogram.domain.model.days.DaysInfo
import ru.geogram.domain.model.days.ProjectInfo
import ru.geogram.domain.model.days.SingleDayInfo

object DaysConverter {
    fun fromNetwork(response: DaysResponse): DaysInfo{
        val days:ArrayList<SingleDayInfo> = ArrayList()

        response.data.days.forEach {
            val projects:ArrayList<ProjectInfo> = ArrayList()
            it.logged_time_records.forEach {
                val project = ProjectInfo(it.project_id, it.project.id,
                    it.project.name, it.description, it.minutes_spent)
                projects.add(project)
            }
            val day = SingleDayInfo(it.date, it.is_working, projects)
            days.add(day)
        }
        return DaysInfo(days)
    }
}