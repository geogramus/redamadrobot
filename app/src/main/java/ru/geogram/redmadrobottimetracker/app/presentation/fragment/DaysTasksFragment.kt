package ru.geogram.redmadrobottimetracker.app.presentation.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_day_of_week.view.*
import ru.geogram.domain.model.days.SingleDayInfo
import ru.geogram.redmadrobottimetracker.app.R
import ru.geogram.redmadrobottimetracker.app.presentation.adapters.ProjectsAdapter

@SuppressLint("ValidFragment")
class DaysTasksFragment(val dayInfo: SingleDayInfo) : Fragment() {

    private lateinit var projectsAdapters: ProjectsAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val fragmentView = inflater.inflate(R.layout.fragment_day_of_week, container, false)
        projectsAdapters = ProjectsAdapter(dayInfo.projectsInfo)
        fragmentView.fragment_day_of_week_tasks_list.adapter = projectsAdapters
        fragmentView.fragment_day_of_week_tasks_list.layoutManager = LinearLayoutManager(activity)
        fragmentView.fragment_day_of_week_date.text = dayInfo.date
        fragmentView.fragment_day_of_week_hours.text = dayInfo.date
        return fragmentView
    }

}