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
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
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
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ErrorViewState
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Loading
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ViewState
import ru.geogram.redmadrobottimetracker.app.utils.*
import saschpe.android.customtabs.CustomTabsHelper
import saschpe.android.customtabs.WebViewFallback
import java.util.concurrent.TimeUnit


class AuthorizationFragment : BaseFragment() {

    companion object {
        fun getInstance(): AuthorizationFragment = AuthorizationFragment()
        const val REDMADROBOT_SITE = "https://redmadrobot.com"
        private val DEBOUNCE_TIME = 200L
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
    private lateinit var screenState: LoadingStateDelegate
    private lateinit var viewModel: AuthoriztionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val fragmentView = inflater.inflate(R.layout.fragment_authorization, container, false)
        screenState = LoadingStateDelegate(fragment_authorization_content, fragment_authorization_progress_bar)
        val viewModelFactory = viewModelFactory { DI.AUTH.get().authViewModel() }
        viewModel = getViewModel(viewModelFactory)
        observe(viewModel.auth, this::onUserChanged)
        viewModel.correctEmail.observe(this, onLoginChanged)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragment_authorization_email_edit_text.textChanges()
                .skipInitialValue()
                .subscribe {
                    viewModel.isValidEmail(it.toString())
                }.disposeOnDetach()

        fragment_authorization_password_question.setOnClickListener(memoryPassword)

        fragment_authorization_auth_btn.clicks()
                .debounce(DEBOUNCE_TIME, TimeUnit.MILLISECONDS)
                .subscribe {
                    viewModel.auth(
                            LoginPassword(
                                    fragment_authorization_email_edit_text.text.toString(),
                                    fragment_authorization_password_edit_text.text.toString()
                            )
                    )
                }.disposeOnDetach()
    }

    private fun onUserChanged(viewState: ViewState) {
        when (viewState) {
            is Data -> {
                viewState.user?.userInfo?.let {
                } ?: {
                    showSnackBar(requireActivity(), getString(R.string.fragment_authorization_error), getString(R.string.ok_string))
                }()
            }
            is Loading -> {
                screenState.showLoading()
            }
            is ErrorViewState -> {
                showSnackBar(requireActivity(), viewState.th.message.toString(), getString(R.string.ok_string))
            }
        }
    }

}