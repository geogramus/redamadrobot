package ru.geogram.redmadrobottimetracker.app.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.geogram.domain.model.days.ProjectInfo
import ru.geogram.redmadrobottimetracker.app.R

class ProjectsAdapter(val projectsInfo: ArrayList<ProjectInfo>) : RecyclerView.Adapter<ProjectsAdapter.WeekTasksViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsAdapter.WeekTasksViewHolder {
        return WeekTasksViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return projectsInfo.size
    }

    override fun onBindViewHolder(holder: ProjectsAdapter.WeekTasksViewHolder, position: Int) {
        val singleDayInfo = projectsInfo.get(position)
        holder.bind(singleDayInfo)
    }

    fun addDays(daysInfo: ArrayList<ProjectInfo>) {
        projectsInfo.clear()
        projectsInfo.addAll(daysInfo)
        notifyDataSetChanged()
    }

    inner class WeekTasksViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private val task_item_hours_amount: TextView = v.findViewById(R.id.task_item_hours_amount)
        private val task_item_description: TextView = v.findViewById(R.id.task_item_description)
        private val task_item_name_of_task: TextView = v.findViewById(R.id.task_item_name_of_task)
        fun bind(singleDayInfo: ProjectInfo) {
            task_item_hours_amount.text = singleDayInfo.minutes_spent.toString()
            task_item_description.text = singleDayInfo.description
            task_item_name_of_task.text = singleDayInfo.name
        }
    }
}