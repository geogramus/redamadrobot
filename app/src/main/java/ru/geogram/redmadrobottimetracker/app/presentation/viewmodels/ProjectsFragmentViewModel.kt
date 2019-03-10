package ru.geogram.redmadrobottimetracker.app.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import com.jakewharton.rxrelay2.PublishRelay
import ru.geogram.domain.model.projects.PayloadInfo
import ru.geogram.domain.model.projects.ProjectInf
import ru.geogram.domain.repositories.ProjectsRepository
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.*
import ru.geogram.redmadrobottimetracker.app.providers.navigation.RouterProvider
import ru.geogram.redmadrobottimetracker.app.utils.schedulersToMain
import javax.inject.Inject

class ProjectsFragmentViewModel @Inject constructor(
        private val projectsService: ProjectsRepository,
        private val provider: RouterProvider
) : BaseViewModel() {

    val projects: MutableLiveData<ProjectsViewState> = MutableLiveData()
    private val projectsList = ArrayList<ProjectInf>()
    val payloadTime: MutableLiveData<ViewState> = MutableLiveData()
    private val publishRelay = PublishRelay.create<String>()

    init {
        loadProjects()
        safeSubscribe {
            publishRelay
                    .map(::filterProjectsByName)
                    .map<ProjectsViewState> {
                        DataProjects(it)
                    }
                    .startWith(LoadingProjects)
                    .onErrorReturn(::ErrorViewStateProjects)
                    .schedulersToMain()
                    .subscribe { projects.value = it }
        }
    }

    private fun loadProjects() {
        safeSubscribe {
            projectsService
                    .getProjects(false)
                    .schedulersToMain()
                    .subscribe(
                            {
                                projectsList.clear()
                                projectsList.addAll(it)
                                projects.postValue(DataProjects(it))
                            },
                            {
                                projects.postValue(
                                        ErrorViewStateProjects(it)
                                )
                                it.printStackTrace()
                            })
        }
    }

    fun payloadTime(payloadInfo: PayloadInfo) {
        payloadTime.postValue(Loading)
        safeSubscribe {
            projectsService
                    .payload(payloadInfo)
                    .schedulersToMain()
                    .subscribe(
                            {
                                payloadTime.postValue(Data(payloadSucces = it))
                            },
                            {
                                payloadTime.postValue(
                                        ErrorViewState(it)
                                )
                                it.printStackTrace()
                            })
        }
    }

    fun onSearchProjectTextChanged(searchString: String) {
        publishRelay.accept(searchString)
    }

    private fun filterProjectsByName(name: String): ArrayList<ProjectInf> {
        val projectsListFinal = ArrayList<ProjectInf>()
        projectsListFinal.addAll(projectsList.filter { it.name.toLowerCase().contains(name.toLowerCase()) }
                .sortedBy { it.name })
        return projectsListFinal
    }

    fun exit() {
        provider.provideRouter().exit()
    }

}