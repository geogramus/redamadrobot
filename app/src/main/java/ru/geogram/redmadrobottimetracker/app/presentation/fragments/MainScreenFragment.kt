package ru.geogram.redmadrobottimetracker.app.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_main_screen.view.*
import ru.geogram.domain.model.days.SingleDayInfo
import ru.geogram.redmadrobottimetracker.app.R
import ru.geogram.redmadrobottimetracker.app.di.DI
import ru.geogram.redmadrobottimetracker.app.presentation.adapters.ViewPagerMonthAdapter
import ru.geogram.redmadrobottimetracker.app.presentation.adapters.ViewPagerTaskAdapter
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.MainScreenViewModel
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Data
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ErrorViewState
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ViewState
import ru.geogram.redmadrobottimetracker.app.utils.getViewModel
import ru.geogram.redmadrobottimetracker.app.utils.observe
import ru.geogram.redmadrobottimetracker.app.utils.showSnackBar
import ru.geogram.redmadrobottimetracker.app.utils.viewModelFactory
import androidx.viewpager.widget.ViewPager
import com.redmadrobot.lib.sd.LoadingStateDelegate
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Loading


class MainScreenFragment : Fragment() {


    private lateinit var viewModel: MainScreenViewModel
    private lateinit var viewPagerTaskAdapter: ViewPagerTaskAdapter
    private lateinit var viewPagerMonthAdapter: ViewPagerMonthAdapter
    private val daysInfo = ArrayList<SingleDayInfo>()
    private lateinit var screenState: LoadingStateDelegate

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val fragmentView = inflater.inflate(R.layout.fragment_main_screen, container, false)
        screenState = LoadingStateDelegate(fragmentView.fragment_main_screen_view_pager_task,
            fragmentView.fragment_main_screen_progress_bar)
        val viewModelFactory = viewModelFactory { DI.DAYS.get().daysTasksFragmentViewModel() }
        viewModel = getViewModel(viewModelFactory)
        viewPagerTaskAdapter = ViewPagerTaskAdapter(fragmentManager!!, daysInfo)
        viewPagerMonthAdapter = ViewPagerMonthAdapter(fragmentManager!!)
        fragmentView.fragment_main_screen_view_pager_date.adapter = viewPagerMonthAdapter
        fragmentView.fragment_main_screen_view_pager_date.setCurrentItem(viewPagerMonthAdapter.getDefaultPosition())
        fragmentView.fragment_main_screen_view_pager_task.adapter = viewPagerTaskAdapter
        observe(viewModel.days, this::onUserChanged)
        fragmentView.fragment_main_screen_view_pager_date.addOnPageChangeListener(changedListener)
        return fragmentView
    }

    private val changedListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            val weekPosition = position - viewPagerMonthAdapter.getDefaultPosition()
            viewModel.loadNewWeek(weekPosition)
        }

        override fun onPageSelected(position: Int) {

        }

        override fun onPageScrollStateChanged(state: Int) {

        }
    }

    private fun onUserChanged(viewState: ViewState) {

        when (viewState) {
            is Data -> {
                screenState.showContent()
                val data = viewState
                data.days?.let {
                    viewPagerTaskAdapter = ViewPagerTaskAdapter(fragmentManager!!, it.days)
                    view?.fragment_main_screen_view_pager_task?.adapter = viewPagerTaskAdapter
                } ?: {
                    showSnackBar(context!!, getString(R.string.fragment_authorization_error), "ок")
                }()
            }
            is Loading ->{
                screenState.showLoading()
            }
            is ErrorViewState -> {
                val data = viewState as ErrorViewState
                showSnackBar(context!!, getString(R.string.fragment_authorization_error_server), "ок")

            }
        }
    }
}