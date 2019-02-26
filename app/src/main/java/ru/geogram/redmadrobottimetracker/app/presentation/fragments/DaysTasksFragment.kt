package ru.geogram.redmadrobottimetracker.app.presentation.fragments

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
import ru.geogram.redmadrobottimetracker.app.di.DI
import ru.geogram.redmadrobottimetracker.app.presentation.Screens
import ru.geogram.redmadrobottimetracker.app.presentation.adapters.ProjectsAdapter
import ru.geogram.redmadrobottimetracker.app.providers.navigation.RouterProvider
import ru.geogram.redmadrobottimetracker.app.utils.Utils
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@SuppressLint("ValidFragment")
class DaysTasksFragment(val dayInfo: SingleDayInfo) : Fragment() {

    private lateinit var projectsAdapters: ProjectsAdapter
    @Inject
    lateinit var router: RouterProvider
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val fragmentView = inflater.inflate(R.layout.fragment_day_of_week, container, false)
        DI.app.inject(this)
        projectsAdapters = ProjectsAdapter(dayInfo.projectsInfoForDays)
        fragmentView.fragment_day_of_week_tasks_list.adapter = projectsAdapters
        fragmentView.fragment_day_of_week_tasks_list.layoutManager = LinearLayoutManager(activity)
        fragmentView.fragment_day_of_week_date.text = Utils.getDayMonth(dayInfo.date)
        fragmentView.fragment_day_of_week_dow.text = getString(Utils.getDayOfWeek(dayInfo.date))
        if (!Utils.getHours(dayInfo.projectsInfoForDays).isEmpty()) {
            val hoursString = Utils.getHours(dayInfo.projectsInfoForDays) + getString(R.string.hour)
            fragmentView.fragment_day_of_week_hours.text = hoursString
        }
        fragmentView.fragment_day_of_week_new_task_btn.setOnClickListener(listener)
        return fragmentView
    }

    val listener = object : View.OnClickListener {
        override fun onClick(v: View?) {
            router.provideRouter().navigateTo(Screens.ShowProjectsFragment)
        }
    }
}