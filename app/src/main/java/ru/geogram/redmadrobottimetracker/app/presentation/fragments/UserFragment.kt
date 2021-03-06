package ru.geogram.redmadrobottimetracker.app.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redmadrobot.lib.sd.LoadingStateDelegate
import kotlinx.android.synthetic.main.fragment_user_info.*
import kotlinx.android.synthetic.main.fragment_user_info.view.*
import ru.geogram.domain.model.auth.UserInfo
import ru.geogram.redmadrobottimetracker.app.R
import ru.geogram.redmadrobottimetracker.app.di.DI
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.UserFragmentViewModel
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Data
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ErrorViewState
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ViewState
import ru.geogram.redmadrobottimetracker.app.utils.getViewModel
import ru.geogram.redmadrobottimetracker.app.utils.observe
import ru.geogram.redmadrobottimetracker.app.utils.showSnackBar
import ru.geogram.redmadrobottimetracker.app.utils.viewModelFactory

class UserFragment : BaseFragment() {

    companion object {
        fun getInstance(): UserFragment = UserFragment()
    }

    private lateinit var screenState: LoadingStateDelegate
    private lateinit var viewModel: UserFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        screenState = LoadingStateDelegate(fragment_user_content)
        val fragmentView = inflater.inflate(R.layout.fragment_user_info, container, false)
        val viewModelFactory = viewModelFactory { DI.AUTH.get().userFragmentViewModel() }
        viewModel = getViewModel(viewModelFactory)
        observe(viewModel.check, this::onUserChanged)

        return fragmentView
    }

    private fun onUserChanged(viewState: ViewState) {
        when (viewState) {
            is Data -> {
                viewState.user?.userInfo?.let {
                    setUserInfo(it)
                } ?: {
                    showSnackBar(
                        requireActivity(),
                        getString(R.string.fragment_authorization_error),
                        getString(R.string.ok_string)
                    )
                }()
            }
            is ErrorViewState -> {
                viewState.th.message?.let {
                    showSnackBar(
                            requireActivity(),
                            it,
                            getString(R.string.ok_string)
                    )
                }

                viewState.user?.userInfo?.let {
                    setUserInfo(it)
                }
            }
        }
    }

    private fun setUserInfo(userInfo: UserInfo) {
        with(userInfo) {
            view?.user_fragment_name?.text = first_name
            view?.user_fragment_last_name?.text = last_name
            view?.user_fragment_email?.text = email
            is_staff?.let {
                if (it) {
                    view?.is_stuff_text_view?.text = getText(ru.geogram.redmadrobottimetracker.app.R.string.is_stuff)
                } else {
                    view?.is_stuff_text_view?.text = getText(ru.geogram.redmadrobottimetracker.app.R.string.not_stuff)
                }
            }
        }
    }

}