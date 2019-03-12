package ru.geogram.redmadrobottimetracker.app.presentation.fragments

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import com.jakewharton.rxbinding3.widget.textChanges
import com.redmadrobot.lib.sd.LoadingStateDelegate
import kotlinx.android.synthetic.main.fragment_projects_list.*
import kotlinx.android.synthetic.main.fragment_projects_list.view.*
import ru.geogram.domain.model.projects.PayloadInfo
import ru.geogram.domain.model.projects.ProjectInf
import ru.geogram.redmadrobottimetracker.app.R
import ru.geogram.redmadrobottimetracker.app.di.DI
import ru.geogram.redmadrobottimetracker.app.presentation.adapters.ProjectsListAdapter
import ru.geogram.redmadrobottimetracker.app.presentation.calbacks.ProjectListCalback
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.ProjectsViewModel
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.*
import ru.geogram.redmadrobottimetracker.app.utils.*
import java.util.concurrent.TimeUnit

class ProjectsFragment : BaseFragment(), ProjectListCalback {

    companion object {
        fun getInstance(date: String): ProjectsFragment {
            val projectsFragment = ProjectsFragment()
            val bundle = Bundle()
            bundle.putString(NEW_DAY_DATE, date)
            projectsFragment.arguments = bundle
            return projectsFragment
        }

        private const val NEW_DAY_DATE = "new_day_date"
        private const val DEBOUNCE_TIME = 300L
        private const val MIN_SEARCH_LETTERS_COUNT = 2
    }

    private val MINUTES_HOUR = 60
    private lateinit var viewModel: ProjectsViewModel
    private lateinit var screenState: LoadingStateDelegate
    private lateinit var projectsAdapter: ProjectsListAdapter
    private var timeIsSet = false
    private var setMinutes = 0
    private var hourOfDay = 0
    private var minutesInHour = 0
    private lateinit var project: ProjectInf
    private var descriptionString = ""
    private var date: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val fragmentView = inflater.inflate(R.layout.fragment_projects_list, container, false)
        val viewModelFactory = viewModelFactory { DI.PROJECTS.get().projectsFragmentViewModel() }
        viewModel = getViewModel(viewModelFactory)
        observe(viewModel.projects, this::onProjectsChanged)
        observe(viewModel.payloadTime, this::onPayloadChanged)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(NEW_DAY_DATE)?.let {
            date = it
        }
        projectsAdapter = ProjectsListAdapter(this)
        fragment_projects_list_recycler.adapter = projectsAdapter
        fragment_projects_list_recycler.layoutManager = LinearLayoutManager(activity)
        screenState = LoadingStateDelegate(
                fragment_projects_list_recycler,
                fragment_projects_list_progress_bar
        )

        fragment_project_list_icn_close.clicks()
                .subscribe {
                    viewModel.exit()
                }
                .disposeOnDetach()

        fragment_project_list_set_time.clicks().subscribe {
            val listener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                this.hourOfDay = hourOfDay
                minutesInHour = minute
                setMinutes = hourOfDay * MINUTES_HOUR + minute
                timeIsSet = true
                fragment_project_list_set_time?.setTextColor(getColor(requireContext(), R.color.grey))
                fragment_project_list_set_time?.text = getString(R.string.time_hours_mins, hourOfDay, minute)
                fragment_project_list_new_project?.isEnable(timeIsSet &&
                        fragment_project_list_add_description?.text?.isNotEmpty()!!)

            }

            TimePickerDialog(activity, listener, hourOfDay, minutesInHour, true).show()
        }
                .disposeOnDetach()
        fragment_project_list_add_description.textChanges()
                .skipInitialValue()
                .subscribe {
                    fragment_project_list_new_project.isEnable(timeIsSet &&
                            it.toString().isNotEmpty())
                    descriptionString = it.toString()
                }
                .disposeOnDetach()
        fragment_project_list_new_project.clicks()
                .subscribe {
                    viewModel.payloadTime(PayloadInfo(project.id, setMinutes, date, descriptionString))
                }
                .disposeOnDetach()

        fragment_projects_list_search_et.afterTextChangeEvents()
                .skipInitialValue()
                .map { it.editable }
                .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
                .filter { it.isEmpty() || it.length >= MIN_SEARCH_LETTERS_COUNT }
                .subscribe { viewModel.onSearchProjectTextChanged(it.toString()) }
                .disposeOnDetach()
    }

    override fun onProjectClick(project: ProjectInf) {
        this.project = project
        view?.fragment_project_list_list?.Gone()
        view?.fragment_project_list_project?.Visible()
        view?.fragment_project_list_project_name?.text = project.name
    }

    private fun onProjectsChanged(viewState: ProjectsViewState) {
        when (viewState) {
            is DataProjects -> {
                screenState.showContent()
                viewState.projects?.let {
                    projectsAdapter.addItems(it)
                }
            }
            is LoadingProjects -> {
                screenState.showLoading()
            }
            is ErrorViewStateProjects -> {
                viewState.error.message?.let {
                    showSnackBar(
                            requireActivity(),
                            it,
                            getString(R.string.ok_string)
                    )
                }
            }
        }
    }

    private fun onPayloadChanged(viewState: ViewState) {
        when (viewState) {
            is Data -> {
                viewState.payloadSucces?.let {
                    showSnackBar(
                            requireActivity(),
                            "${it.project_name} ${getString(R.string.succes_writing)}",
                            getString(R.string.ok_string)
                    )
                }
                viewModel.exit()
            }
            is ErrorViewState -> {
                viewState.th.message?.let {
                    showSnackBar(
                            requireActivity(),
                            it,
                            getString(R.string.ok_string)
                    )
                }
            }
        }
    }

}