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
import com.redmadrobot.lib.sd.LoadingStateDelegate
import kotlinx.android.synthetic.main.fragment_projects_list.view.*
import ru.geogram.domain.model.projects.PayloadInfo
import ru.geogram.domain.model.projects.ProjectInf
import ru.geogram.redmadrobottimetracker.app.R
import ru.geogram.redmadrobottimetracker.app.di.DI
import ru.geogram.redmadrobottimetracker.app.presentation.adapters.ProjectsListAdapter
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.ProjectsFragmentViewModel
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Data
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ErrorViewState
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Loading
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ViewState
import ru.geogram.redmadrobottimetracker.app.providers.navigation.RouterProvider
import ru.geogram.redmadrobottimetracker.app.utils.*
import javax.inject.Inject

@SuppressLint("ValidFragment")
class ProjectsFragment(val date: String) : Fragment(), ProjectsListAdapter.ProjectListCalback {

    private val MINUTES_HOUR = 60
    private lateinit var viewModel: ProjectsFragmentViewModel
    private lateinit var screenState: LoadingStateDelegate
    private lateinit var projectsAdapter: ProjectsListAdapter
    @Inject
    lateinit var router: RouterProvider
    private var timeIsSet = false
    private var setMinutes = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val fragmentView = inflater.inflate(R.layout.fragment_projects_list, container, false)
        DI.app.inject(this)
        projectsAdapter = ProjectsListAdapter(this)
        fragmentView.fragment_projects_list_recycler.adapter = projectsAdapter
        fragmentView.fragment_projects_list_recycler.layoutManager = LinearLayoutManager(activity)
        screenState = LoadingStateDelegate(fragmentView.fragment_projects_list_recycler,
                fragmentView.fragment_projects_list_progress_bar)
        val viewModelFactory = viewModelFactory { DI.PROJECTS.get().projectsFragmentViewModel() }
        viewModel = getViewModel(viewModelFactory)
        observe(viewModel.projects, this::onProjectsChanged)
        observe(viewModel.payloadTime, this::onPayloadChanged)
        fragmentView.fragment_project_list_icn_close.setOnClickListener(listenerCancel)
        fragmentView.fragment_project_list_set_time.setOnClickListener(listenerSetDate)
        fragmentView.fragment_project_list_add_description.addTextChangedListener(descriptionWatcher)
        fragmentView.fragment_project_list_add.setOnClickListener(listenerButton)
        return fragmentView
    }


    private fun onProjectsChanged(viewState: ViewState) {

        when (viewState) {
            is Data -> {
                screenState.showContent()
                viewState.projects?.let {
                    projectsAdapter.addItems(it.projectList)
                }
            }
            is Loading -> {
                screenState.showLoading()
            }
            is ErrorViewState -> {
                showSnackBar(context!!, getString(R.string.fragment_authorization_error_server), "ок")
            }
        }
    }

    private fun onPayloadChanged(viewState: ViewState) {

        when (viewState) {
            is Data -> {
                viewState.payloadSucces?.let {
                    showSnackBar(context!!, "${it.project_name} ${getString(R.string.succes_writing)}", "ок")
                }
                router.provideRouter().exit()
            }
            is ErrorViewState -> {
                showSnackBar(context!!, getString(R.string.fragment_authorization_error_server), "ок")
            }
        }
    }


    val listenerCancel = object : View.OnClickListener {
        override fun onClick(v: View?) {
            router.provideRouter().exit()
        }
    }

    val listenerSetDate = object : View.OnClickListener {
        override fun onClick(v: View?) {
            val listener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                setMinutes = hourOfDay * MINUTES_HOUR + minute
                timeIsSet = true
                val timeString = "${hourOfDay}ч. ${minute}мин."
                view?.fragment_project_list_set_time?.setTextColor( getColor(context!!, R.color.grey))
                view?.fragment_project_list_set_time?.text = timeString
                if (timeIsSet && view?.fragment_project_list_add_description?.text?.isNotEmpty()!!) {
                    view?.fragment_project_list_add?.enable()
                }
            }
            TimePickerDialog(activity, listener, 0, 0, true).show()
        }
    }

    val listenerButton = object : View.OnClickListener {
        override fun onClick(v: View?) {
            viewModel.payloadTime(PayloadInfo(project.id, setMinutes, date, descriptionString))
        }
    }

    private var descriptionString = ""
    val descriptionWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (timeIsSet && s.toString().isNotEmpty()) {
                descriptionString = s.toString()
                view?.fragment_project_list_add?.enable()
            } else {
                view?.fragment_project_list_add?.disable()
            }
        }
    }
    private lateinit var project: ProjectInf
    override fun onProjectClick(project: ProjectInf) {
        this.project = project
        view?.fragment_project_list_list?.Gone()
        view?.fragment_project_list_project?.Visible()
        view?.fragment_project_list_project_name?.text = project.name
    }
}