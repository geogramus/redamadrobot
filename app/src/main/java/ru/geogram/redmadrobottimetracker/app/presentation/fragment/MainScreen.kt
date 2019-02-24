package ru.geogram.redmadrobottimetracker.app.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main_screen.view.*
import ru.geogram.domain.model.days.SingleDayInfo
import ru.geogram.redmadrobottimetracker.app.R
import ru.geogram.redmadrobottimetracker.app.di.DI
import ru.geogram.redmadrobottimetracker.app.presentation.adapters.ViewPagerTaskAdapter
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.MainScreenViewModel
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Data
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ErrorViewState
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ViewState
import ru.geogram.redmadrobottimetracker.app.utils.getViewModel
import ru.geogram.redmadrobottimetracker.app.utils.observe
import ru.geogram.redmadrobottimetracker.app.utils.showSnackBar
import ru.geogram.redmadrobottimetracker.app.utils.viewModelFactory

class MainScreen : Fragment() {
    private lateinit var viewModel: MainScreenViewModel
    private lateinit var viewPagerTaskAdapter: ViewPagerTaskAdapter
    private val daysInfo = ArrayList<SingleDayInfo>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val fragmentView = inflater.inflate(R.layout.fragment_main_screen, container, false)
        val viewModelFactory = viewModelFactory { DI.DAYS.get().daysTasksFragmentViewModel() }
        viewModel = getViewModel(viewModelFactory)
        viewPagerTaskAdapter = ViewPagerTaskAdapter(fragmentManager!!, daysInfo)
        fragmentView.fragment_main_screen_view_pager_task.adapter = viewPagerTaskAdapter
        observe(viewModel.days, this::onUserChanged)
        return fragmentView
    }

    private fun onUserChanged(viewState: ViewState) {
        when (viewState) {
            is Data -> {
                val data = viewState
                data.days?.let {
                    viewPagerTaskAdapter.addDays(it.days)
                } ?: {
                    showSnackBar(context!!, getString(R.string.fragment_authorization_error), "ок")
                }()
            }
            is ErrorViewState -> {
                val data = viewState as ErrorViewState
                showSnackBar(context!!, getString(R.string.fragment_authorization_error_server), "ок")

            }
        }
    }
}