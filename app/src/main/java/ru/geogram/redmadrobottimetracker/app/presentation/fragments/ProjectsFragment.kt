package ru.geogram.redmadrobottimetracker.app.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.redmadrobot.lib.sd.LoadingStateDelegate
import kotlinx.android.synthetic.main.fragment_projects_list.view.*
import ru.geogram.redmadrobottimetracker.app.R
import ru.geogram.redmadrobottimetracker.app.di.DI
import ru.geogram.redmadrobottimetracker.app.presentation.adapters.ProjectsAdapter
import ru.geogram.redmadrobottimetracker.app.presentation.adapters.ProjectsListAdapter
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.ProjectsFragmentViewModel
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Data
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ErrorViewState
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Loading
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ViewState
import ru.geogram.redmadrobottimetracker.app.utils.getViewModel
import ru.geogram.redmadrobottimetracker.app.utils.observe
import ru.geogram.redmadrobottimetracker.app.utils.showSnackBar
import ru.geogram.redmadrobottimetracker.app.utils.viewModelFactory

class ProjectsFragment : Fragment() {

    private lateinit var viewModel: ProjectsFragmentViewModel
    private lateinit var screenState: LoadingStateDelegate
    private lateinit var projectsAdapter:ProjectsListAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val fragmentView = inflater.inflate(R.layout.fragment_projects_list, container, false)
        projectsAdapter = ProjectsListAdapter()
        fragmentView.fragment_projects_list_recycler.adapter = projectsAdapter
        fragmentView.fragment_projects_list_recycler.layoutManager = LinearLayoutManager(activity)
        screenState = LoadingStateDelegate(fragmentView.fragment_projects_list_recycler,
                fragmentView.fragment_projects_list_progress_bar)
        val viewModelFactory = viewModelFactory { DI.PROJECTS.get().projectsFragmentViewModel()}
        viewModel = getViewModel(viewModelFactory)
        observe(viewModel.projects, this::onUserChanged)
        return fragmentView
    }


    private fun onUserChanged(viewState: ViewState) {

        when (viewState) {
            is Data -> {
                screenState.showContent()
                viewState.projects?.let {
                    projectsAdapter.addItems(it.projectList)
                }
            }
            is Loading ->{
                screenState.showLoading()
            }
            is ErrorViewState -> {
                showSnackBar(context!!, getString(R.string.fragment_authorization_error_server), "ок")
            }
        }
    }
}