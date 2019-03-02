package ru.geogram.redmadrobottimetracker.app.presentation.holders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.geogram.domain.model.days.ProjectInfoForDays
import ru.geogram.redmadrobottimetracker.app.R
import ru.geogram.redmadrobottimetracker.app.utils.Utils

class WeekTasksViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    private val task_item_hours_amount: TextView = v.findViewById(R.id.task_item_hours_amount)
    private val task_item_description: TextView = v.findViewById(R.id.task_item_description)
    private val task_item_name_of_task: TextView = v.findViewById(R.id.task_item_name_of_task)

    fun bind(singleDayInfoForDays: ProjectInfoForDays) {
        task_item_hours_amount.text = Utils.getHoursForSingleDay(singleDayInfoForDays.minutes_spent)
        task_item_description.text = singleDayInfoForDays.description
        task_item_name_of_task.text = singleDayInfoForDays.name
    }
}