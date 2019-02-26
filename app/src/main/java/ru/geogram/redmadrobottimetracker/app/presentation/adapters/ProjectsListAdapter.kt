package ru.geogram.redmadrobottimetracker.app.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.geogram.domain.model.projects.ProjectInf
import ru.geogram.redmadrobottimetracker.app.R

class ProjectsListAdapter : RecyclerView.Adapter<ProjectsListAdapter.ProjectsListViewHolder>() {
    val projectsList = ArrayList<ProjectInf>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsListAdapter.ProjectsListViewHolder {
        return ProjectsListViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.project_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return projectsList.size
    }

    override fun onBindViewHolder(holder: ProjectsListAdapter.ProjectsListViewHolder, position: Int) {
       holder.bind(position)
    }

    fun addItems(items : ArrayList<ProjectInf>){
        projectsList.clear()
        projectsList.addAll(items)
        notifyDataSetChanged()
    }

    inner class ProjectsListViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        private val project_item_tv: TextView = v.findViewById(R.id.project_item_tv)
        fun bind(position: Int) {
            project_item_tv.text = projectsList.get(position).name
        }
    }
}