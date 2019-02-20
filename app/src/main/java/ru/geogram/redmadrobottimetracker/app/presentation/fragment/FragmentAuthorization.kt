package ru.geogram.redmadrobottimetracker.app.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.geogram.redmadrobottimetracker.app.R
import com.redmadrobot.lib.sd.LoadingStateDelegate
import kotlinx.android.synthetic.main.fragment_authorization.*
import kotlinx.android.synthetic.main.fragment_authorization.view.*
import ru.geogram.domain.model.auth.LoginPassword
import ru.geogram.domain.model.auth.UserInfo
import ru.geogram.redmadrobottimetracker.app.di.DI
import ru.geogram.redmadrobottimetracker.app.presentation.presenter.AuthoriztionViewModel
import ru.geogram.redmadrobottimetracker.app.presentation.presenter.Data
import ru.geogram.redmadrobottimetracker.app.presentation.presenter.Loading
import ru.geogram.redmadrobottimetracker.app.presentation.presenter.UserViewState
import ru.geogram.redmadrobottimetracker.app.utils.getViewModel
import ru.geogram.redmadrobottimetracker.app.utils.observe
import ru.geogram.redmadrobottimetracker.app.utils.showSnackBar
import ru.geogram.redmadrobottimetracker.app.utils.viewModelFactory


class FragmentAuthorization : Fragment() {
    private val okString = "Ок"
    private lateinit var screenState: LoadingStateDelegate
    private lateinit var viewModel: AuthoriztionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val fragmentView = inflater.inflate(R.layout.fragment_authorization, container, false)
        screenState = LoadingStateDelegate(fragment_authorization_content, fragment_authorization_progress_bar)
        val viewModelFactory = viewModelFactory { DI.user.get().authViewModel() }
        viewModel = getViewModel(viewModelFactory)
        fragmentView.fragment_authorization_auth_btn.setOnClickListener {
            viewModel.auth(LoginPassword(fragmentView.fragment_authorization_email_edit_text.text.toString(),
                    fragmentView.fragment_authorization_password_edit_text.text.toString()))
        }
        observe(viewModel.user, this::onUserChanged)
        return fragmentView
    }

    private fun onUserChanged(viewState: UserViewState) {
        when (viewState) {
            is Data -> {
                val data = viewState as Data
                data.user?.userInfo?.let {
                    fragmentAuthorization.showUserFragment()
                } ?: {
                    showSnackBar(context!!, getString(R.string.fragment_authorization_error), okString)
                }()
            }
            is Loading -> {
                screenState.showLoading()
            }
            is Error -> {
                val error = viewState as Error
                error.message?.let {
                    showSnackBar(context!!, it, okString)
                }
            }
        }
    }

    lateinit var fragmentAuthorization: FragmentAuthorizationInterface

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (activity is FragmentAuthorizationInterface) {
            fragmentAuthorization = context as FragmentAuthorizationInterface
        } else {
            throw UnsupportedOperationException("Activity must implement FragmentAuthorizationInterface")
        }
    }

    interface FragmentAuthorizationInterface {
        fun showUserFragment()
    }
}