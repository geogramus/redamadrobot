package ru.geogram.redmadrobottimetracker.app.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import ru.geogram.redmadrobottimetracker.app.R
import com.redmadrobot.lib.sd.LoadingStateDelegate
import kotlinx.android.synthetic.main.fragment_authorization.*
import kotlinx.android.synthetic.main.fragment_authorization.view.*
import ru.geogram.domain.model.auth.LoginPassword
import ru.geogram.redmadrobottimetracker.app.di.DI
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.AuthoriztionViewModel
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Data
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Loading
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ViewState
import ru.geogram.redmadrobottimetracker.app.utils.*


class AuthorizationFragment : Fragment() {
    private val okString = "Ок"
    private lateinit var screenState: LoadingStateDelegate
    private lateinit var viewModel: AuthoriztionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val fragmentView = inflater.inflate(R.layout.fragment_authorization, container, false)
        screenState = LoadingStateDelegate(fragment_authorization_content, fragment_authorization_progress_bar)
        val viewModelFactory = viewModelFactory { DI.AUTH.get().authViewModel() }
        viewModel = getViewModel(viewModelFactory)

        fragmentView.fragment_authorization_auth_btn.setOnClickListener {
            viewModel.auth(LoginPassword(fragmentView.fragment_authorization_email_edit_text.text.toString(),
                    fragmentView.fragment_authorization_password_edit_text.text.toString()))

        }

        observe(viewModel.auth, this::onUserChanged)
        viewModel.correctEmail.observe(this, onLoginChanged)
        fragmentView.fragment_authorization_email_edit_text.addTextChangedListener(textWatcher)

        return fragmentView
    }

    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(email: Editable?) {
            viewModel.isValidEmail(email.toString())
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }
    val onLoginChanged = Observer<Boolean>() { loginCorrect ->
        loginCorrect?.let {
            if (!it) {
                fragment_authorization_content_not_valid.Visible()
            } else {
                fragment_authorization_content_not_valid.Gone()
            }
        }
    }

    private fun onUserChanged(viewState: ViewState) {
        when (viewState) {
            is Data -> {
                screenState.showContent()
                val data = viewState
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
                val error = viewState
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