package ru.geogram.redmadrobottimetracker.app.presentation.holders

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.geogram.domain.model.projects.ProjectInf
import ru.geogram.redmadrobottimetracker.app.R
import ru.geogram.redmadrobottimetracker.app.presentation.calbacks.ProjectListCalback

class ProjectsListViewHolder(v: View) : RecyclerView.ViewHolder(v) {

    private val project_item_container: LinearLayout = v.findViewById(R.id.project_item_container)
    private val project_item_tv: TextView = v.findViewById(R.id.project_item_tv)

    fun bind(project: ProjectInf, listener: ProjectListCalback) {
        project_item_tv.text = project.name
        project_item_container.setOnClickListener {
            listener.onProjectClick(project)
        }
    }
}

