package ru.geogram.redmadrobottimetracker.app.presentation.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import ru.geogram.redmadrobottimetracker.app.R
import com.redmadrobot.lib.sd.LoadingStateDelegate
import kotlinx.android.synthetic.main.fragment_authorization.*
import kotlinx.android.synthetic.main.fragment_authorization.view.*
import ru.geogram.data.network.ServerUrls
import ru.geogram.domain.model.auth.LoginPassword
import ru.geogram.domain.model.projects.PayloadInfo
import ru.geogram.redmadrobottimetracker.app.di.DI
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.AuthoriztionViewModel
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Data
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Loading
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ViewState
import ru.geogram.redmadrobottimetracker.app.utils.*
import saschpe.android.customtabs.CustomTabsHelper
import saschpe.android.customtabs.WebViewFallback


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
        fragmentView.fragment_authorization_password_question.setOnClickListener(memoryPassword)
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

    val memoryPassword = object : View.OnClickListener {
        override fun onClick(v: View?) {
            val customTabsIntent = CustomTabsIntent.Builder()
                .addDefaultShareMenuItem()
                .setToolbarColor(
                    context?.resources?.getColor(R.color.grey)!!
                )
                .setShowTitle(true)
                .build()

            CustomTabsHelper.addKeepAliveExtra(context, customTabsIntent.intent)

            CustomTabsHelper.openCustomTab(
                context, customTabsIntent,
                Uri.parse(REDMADROBOT_SITE),
                WebViewFallback()
            )
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



    companion object {
        const val REDMADROBOT_SITE = "https://redmadrobot.com"
    }
}