package ru.geogram.redmadrobottimetracker.app.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import com.redmadrobot.lib.sd.LoadingStateDelegate
import kotlinx.android.synthetic.main.fragment_registration.*
import ru.geogram.redmadrobottimetracker.app.R
import ru.geogram.redmadrobottimetracker.app.di.DI
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.RegistrationViewModel
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Data
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ErrorViewState
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Loading
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ViewState
import ru.geogram.redmadrobottimetracker.app.utils.*

class RegistrationFragment : BaseFragment() {

    companion object {
        fun getInstance(): RegistrationFragment = RegistrationFragment()
    }

    private lateinit var viewModel: RegistrationViewModel
    private lateinit var screenState: LoadingStateDelegate

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val viewModelFactory = viewModelFactory { DI.AUTH.get().registrationViewModel() }
        viewModel = getViewModel(viewModelFactory)
        screenState = LoadingStateDelegate(
                fragment_registration_content,
                fragment_registration_progress_bar
        )
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragment_registration_name.afterTextChangeEvents()
                .skipInitialValue()
                .subscribe {
                    allFieldsAreValid()
                }.disposeOnDetach()
        fragment_registration_last_name.afterTextChangeEvents()
                .skipInitialValue()
                .subscribe {
                    allFieldsAreValid()
                }.disposeOnDetach()
        fragment_registration_email.afterTextChangeEvents()
                .skipInitialValue()
                .subscribe {
                    allFieldsAreValid()
                }.disposeOnDetach()
        fragment_registration_password.afterTextChangeEvents()
                .skipInitialValue().subscribe {
                    allFieldsAreValid()
                }.disposeOnDetach()
        fragment_registration_registration_button.clicks()
                .subscribe {
                    viewModel.registrate(
                            fragment_registration_name.text.toString(),
                            fragment_registration_last_name.text.toString(),
                            fragment_registration_email.text.toString(),
                            fragment_registration_password.text.toString()
                    )
                }.disposeOnDetach()
        fragment_registration_back_btn.clicks()
                .subscribe{
                    viewModel.exit()
                }.disposeOnDetach()
        observe(viewModel.allFieldsCorrect, ::allFieldsValidCheck)
        observe(viewModel.registrate, ::onUserChanged)
    }

    private fun allFieldsValidCheck(valid: Boolean) {
        if (valid) {
            fragment_registration_registration_button.isEnable(valid)
        }
    }

    private fun allFieldsAreValid() {
        viewModel.allFieldsAreValid(
                fragment_registration_name.text.toString(),
                fragment_registration_last_name.text.toString(),
                fragment_registration_email.text.toString(),
                fragment_registration_password.text.toString()
        )
    }

    private fun onUserChanged(viewState: ViewState) {
        when (viewState) {
            is Data -> {
                if (viewState.user?.userInfo == null) {
                    showSnackBar(
                            requireActivity(),
                            getString(R.string.fragment_authorization_error),
                            getString(R.string.ok_string)
                    )
                }else{
                    viewModel.exit()
                }
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