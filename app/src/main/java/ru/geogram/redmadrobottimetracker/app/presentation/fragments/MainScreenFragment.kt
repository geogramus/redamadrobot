package ru.geogram.redmadrobottimetracker.app.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.jakewharton.rxbinding3.view.clicks
import com.redmadrobot.lib.sd.LoadingStateDelegate
import kotlinx.android.synthetic.main.fragment_main_screen.*
import kotlinx.android.synthetic.main.fragment_main_screen.view.*
import ru.geogram.domain.model.days.SingleDayInfo
import ru.geogram.redmadrobottimetracker.app.R
import ru.geogram.redmadrobottimetracker.app.di.DI
import ru.geogram.redmadrobottimetracker.app.presentation.adapters.ViewPagerTaskAdapter
import ru.geogram.redmadrobottimetracker.app.presentation.adapters.ViewPagerWeekAdapter
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.MainScreenViewModel
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Data
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ErrorViewState
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Loading
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ViewState
import ru.geogram.redmadrobottimetracker.app.utils.*


class MainScreenFragment : BaseFragment() {

    companion object {
        fun getInstance(): MainScreenFragment = MainScreenFragment()
    }

    private lateinit var viewModel: MainScreenViewModel
    private lateinit var viewPagerTaskAdapter: ViewPagerTaskAdapter
    private lateinit var viewPagerWeekAdapter: ViewPagerWeekAdapter
    private val daysInfo = ArrayList<SingleDayInfo>()
    private lateinit var screenState: LoadingStateDelegate
    private val nextPreviousDatePosition = 1
    private val changedListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            val weekPosition = position - viewPagerWeekAdapter.getDefaultPosition()
            viewModel.loadNewWeek(weekPosition)
        }

        override fun onPageSelected(position: Int) {

        }

        override fun onPageScrollStateChanged(state: Int) {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val fragmentView = inflater.inflate(R.layout.fragment_main_screen, container, false)
        val viewModelFactory = viewModelFactory { DI.DAYS.get().daysTasksFragmentViewModel() }
        viewModel = getViewModel(viewModelFactory)

        observe(viewModel.days, this::onUserChanged)
        observe(viewModel.threats, this::onThreatsDetected)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        screenState = LoadingStateDelegate(
                fragment_main_screen_view_pager_task,
                fragment_main_screen_progress_bar
        )
        viewPagerTaskAdapter = ViewPagerTaskAdapter(requireFragmentManager(), daysInfo)
        viewPagerWeekAdapter = ViewPagerWeekAdapter(getChildFragmentManager())
        fragment_main_screen_view_pager_date.adapter = viewPagerWeekAdapter
        fragment_main_screen_view_pager_date.setCurrentItem(viewPagerWeekAdapter.getDefaultPosition())
        fragment_main_screen_view_pager_task.adapter = viewPagerTaskAdapter
        fragment_main_screen_view_pager_date.addOnPageChangeListener(changedListener)
        fragment_main_screen_arrow_back.clicks().subscribe {
            fragment_main_screen_view_pager_date.setCurrentItem(
                    fragment_main_screen_view_pager_date.currentItem - nextPreviousDatePosition,
                    true
            )
        }.disposeOnDetach()
        fragment_main_screen_arrow_forward.clicks().subscribe {
            fragment_main_screen_view_pager_date.setCurrentItem(
                    fragment_main_screen_view_pager_date.currentItem + nextPreviousDatePosition,
                    true
            )
        }.disposeOnDetach()
    }

    private fun onUserChanged(viewState: ViewState) {

        when (viewState) {
            is Data -> {
                screenState.showContent()
                viewState.days?.let {
                    viewPagerTaskAdapter = ViewPagerTaskAdapter(requireFragmentManager(), it.days)
                    view?.fragment_main_screen_view_pager_task?.adapter = viewPagerTaskAdapter
                } ?: {
                    showSnackBar(
                            requireActivity(),
                            getString(R.string.fragment_authorization_error),
                            getString(R.string.ok_string)
                    )
                }()
            }
            is Loading -> {
                screenState.showLoading()
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

    private fun onThreatsDetected(threats: List<String>) {
        if (threats.isNotEmpty()) {
            context?.let {
                showThreatAlertDialog(it, messages = threats)
            }
        }
    }
}