package ru.geogram.redmadrobottimetracker.app.presentation.fragments

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
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
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.ProjectsFragmentViewModel
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.*
import ru.geogram.redmadrobottimetracker.app.providers.navigation.RouterProvider
import ru.geogram.redmadrobottimetracker.app.utils.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ProjectsFragment : BaseFragment(), ProjectListCalback {

    companion object {
        fun getInstance(): ProjectsFragment = ProjectsFragment()
        private const val DEBOUNCE_TIME = 300L
        private const val MIN_SEARCH_LETTERS_COUNT = 1
    }

    private val MINUTES_HOUR = 60
    private lateinit var viewModel: ProjectsFragmentViewModel
    private lateinit var screenState: LoadingStateDelegate
    private lateinit var projectsAdapter: ProjectsListAdapter
    @Inject
    lateinit var router: RouterProvider
    private var timeIsSet = false
    private var setMinutes = 0
    private lateinit var project: ProjectInf
    private var descriptionString = ""
    private var date: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val fragmentView = inflater.inflate(R.layout.fragment_projects_list, container, false)
        DI.PROJECTS.get().inject(this)
        val viewModelFactory = viewModelFactory { DI.PROJECTS.get().projectsFragmentViewModel() }
        viewModel = getViewModel(viewModelFactory)
        observe(viewModel.projects, this::onProjectsChanged)
        observe(viewModel.payloadTime, this::onPayloadChanged)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(DaysTasksFragment.NEW_DAY_DATE)?.let {
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
                    router.provideRouter().exit()
                }
                .disposeOnDetach()

        fragment_project_list_set_time.clicks().subscribe {
            val listener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                setMinutes = hourOfDay * MINUTES_HOUR + minute
                timeIsSet = true
                fragment_project_list_set_time?.setTextColor(getColor(requireContext(), R.color.grey))
                fragment_project_list_set_time?.text = getString(R.string.time_hours_mins, hourOfDay, minute)
                fragment_project_list_new_project?.isEnable(timeIsSet && fragment_project_list_add_description?.text?.isNotEmpty()!!)

            }
            TimePickerDialog(activity, listener, 0, 0, true).show()
        }
                .disposeOnDetach()
        fragment_project_list_add_description.textChanges()
                .skipInitialValue()
                .subscribe {
                    fragment_project_list_new_project.isEnable(timeIsSet && it.toString().isNotEmpty())
                    descriptionString = it.toString()
                }
                .disposeOnDetach()
        fragment_project_list_new_project.clicks()
                .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
                .subscribe {
                    viewModel.payloadTime(PayloadInfo(project.id, setMinutes, date, descriptionString))
                }
                .disposeOnDetach()

        fragment_projects_list_search_et.afterTextChangeEvents()
                .skipInitialValue()
                .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
                .filter { it.editable?.isEmpty() == true || it.editable?.length ?: 0 >= MIN_SEARCH_LETTERS_COUNT }
                .subscribe { viewModel.onSearchProjectTextChanged(it.editable.toString()) }
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
                    projectsAdapter.addItems(it.projectList)
                }
            }
            is LoadingProjects -> {
                screenState.showLoading()
            }
            is ErrorViewStateProjects -> {
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
                router.provideRouter().exit()
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