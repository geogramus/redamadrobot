package ru.geogram.redmadrobottimetracker.app.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.geogram.domain.model.days.ProjectInfoForDays
import ru.geogram.redmadrobottimetracker.app.R
import ru.geogram.redmadrobottimetracker.app.presentation.holders.WeekTasksViewHolder

class ProjectsAdapter(val projectsInfoForDays: ArrayList<ProjectInfoForDays>) :
    RecyclerView.Adapter<WeekTasksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekTasksViewHolder =
        WeekTasksViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        )


    override fun getItemCount(): Int = projectsInfoForDays.size


    override fun onBindViewHolder(holder: WeekTasksViewHolder, position: Int) {
        val singleDayInfo = projectsInfoForDays.get(position)
        holder.bind(singleDayInfo)
    }

    fun addDays(daysInfoForDays: ArrayList<ProjectInfoForDays>) {
        projectsInfoForDays.clear()
        projectsInfoForDays.addAll(daysInfoForDays)
        notifyDataSetChanged()
    }

}