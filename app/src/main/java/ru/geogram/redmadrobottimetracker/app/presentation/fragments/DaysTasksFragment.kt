package ru.geogram.redmadrobottimetracker.app.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.jakewharton.rxbinding3.view.clicks
import kotlinx.android.synthetic.main.fragment_day_of_week.*
import ru.geogram.domain.model.days.SingleDayInfo
import ru.geogram.redmadrobottimetracker.app.R
import ru.geogram.redmadrobottimetracker.app.di.DI
import ru.geogram.redmadrobottimetracker.app.presentation.ShowProjectsFragment
import ru.geogram.redmadrobottimetracker.app.presentation.adapters.ProjectsAdapter
import ru.geogram.redmadrobottimetracker.app.providers.navigation.RouterProvider
import ru.geogram.redmadrobottimetracker.app.utils.Utils
import javax.inject.Inject


class DaysTasksFragment : BaseFragment() {

    companion object {
        fun getInstance(): DaysTasksFragment = DaysTasksFragment()
        const val DAY_TASKS = "day_position"
    }

    private lateinit var projectsAdapters: ProjectsAdapter
    @Inject
    lateinit var router: RouterProvider
    private var dayInfo: SingleDayInfo? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val fragmentView = inflater.inflate(R.layout.fragment_day_of_week, container, false)
        DI.DAYS.get().inject(this)
        arguments?.getString(DAY_TASKS)?.let {
            dayInfo = Gson().fromJson(it, SingleDayInfo::class.java)
        }
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dayInfo?.let {
            projectsAdapters = ProjectsAdapter(it.projectsInfoForDays)
            fragment_day_of_week_tasks_list.adapter = projectsAdapters
            fragment_day_of_week_tasks_list.layoutManager = LinearLayoutManager(activity)
            fragment_day_of_week_date.text = Utils.getDayMonth(it.date)
            fragment_day_of_week_dow.text = getString(Utils.getDayOfWeek(it.date))
            if (!Utils.getHours(it.projectsInfoForDays).isEmpty()) {
                val hoursString = Utils.getHours(it.projectsInfoForDays) + getString(R.string.hour)
                fragment_day_of_week_hours.text = hoursString
            }
        }
        fragment_day_of_week_new_task_btn.clicks().subscribe{
            dayInfo?.date?.let {
                router.provideRouter().navigateTo(ShowProjectsFragment(it))
            }
        }.disposeOnDetach()
    }
}