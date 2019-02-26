package ru.geogram.redmadrobottimetracker.app.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import ru.geogram.domain.repositories.ProjectsRepository
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Data
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ErrorViewState
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Loading
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ViewState
import ru.geogram.redmadrobottimetracker.app.providers.navigation.RouterProvider
import ru.geogram.redmadrobottimetracker.app.utils.applySchedulers
import javax.inject.Inject

class ProjectsFragmentViewModel @Inject constructor(
        private val projectsService: ProjectsRepository,
        private val provider: RouterProvider
) : BaseViewModel() {

    val projects: MutableLiveData<ViewState> = MutableLiveData()

    init {
        loadProjects()
    }

    private fun loadProjects() {
        projects.postValue(Loading)
        val disposable = projectsService
                .getProjects(false)
                .compose(applySchedulers())
                .subscribe({
                    projects.postValue(Data(projects = it))
                },
                        {
                            projects.postValue(
                                    ErrorViewState(it)
                            )
                            it.printStackTrace()
                        })
        safeSubscribe { disposable }
    }

}