package ru.geogram.redmadrobottimetracker.app.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.geogram.domain.model.projects.ProjectInf
import ru.geogram.redmadrobottimetracker.app.R
import ru.geogram.redmadrobottimetracker.app.presentation.calbacks.ProjectListCalback
import ru.geogram.redmadrobottimetracker.app.presentation.holders.ProjectsListViewHolder

class ProjectsListAdapter(val listener: ProjectListCalback) : RecyclerView.Adapter<ProjectsListViewHolder>() {

    val projectsList = ArrayList<ProjectInf>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsListViewHolder =
    ProjectsListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.project_item, parent, false))

    override fun getItemCount(): Int = projectsList.size

    override fun onBindViewHolder(holder: ProjectsListViewHolder, position: Int) {
        val project = projectsList.get(position)
        holder.bind(project, listener)
    }

    fun addItems(items: ArrayList<ProjectInf>) {
        projectsList.clear()
        projectsList.addAll(items)
        notifyDataSetChanged()
    }
}