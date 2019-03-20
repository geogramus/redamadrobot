package ru.geogram.redmadrobottimetracker.app.presentation.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.fragment_enter_pin.*
import ru.geogram.redmadrobottimetracker.app.R
import ru.geogram.redmadrobottimetracker.app.di.DI
import ru.geogram.redmadrobottimetracker.app.presentation.asynch.MainThreadExec
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.EnterPinViewModel
import ru.geogram.redmadrobottimetracker.app.utils.*

class EnterPinFragment : BaseKeyboardFragment() {

    companion object {
        fun getInstance(): EnterPinFragment = EnterPinFragment()
    }

    private lateinit var viewModel: EnterPinViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val viewModelFactory = viewModelFactory { DI.AUTH.get().createEnterPinViewModel() }
        viewModel = getViewModel(viewModelFactory)
        return inflater.inflate(R.layout.fragment_enter_pin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNumbersListeners()
        fragment_enter_pin_et.textChanges()
            .subscribe {
                viewModel.isPinValid(it.toString())
            }.disposeOnDetach()
        fragment_enter_pin_remove_btn.clicks()
            .subscribe {
                fragment_enter_pin_incorrect_pin_tv.Gone()
                removeLastSimbol()
            }.disposeOnDetach()
        fragment_enter_pin_finger_button.clicks()
            .subscribe {
                useBiometric()
            }.disposeOnDetach()
        observe(viewModel.isValidPin, this::onPinChanged)
        observe(viewModel.usingFingerScan, this::onUseFingerChanged)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun pinStringChanged(pin: String) = fragment_enter_pin_et
        .setText(pin, TextView.BufferType.EDITABLE)

    private fun onPinChanged(pinIsCorrect: Boolean) {
        if (pinIsCorrect) viewModel.showMainScreen() else
            fragment_enter_pin_incorrect_pin_tv.Visible()
    }

    private fun onUseFingerChanged(useFinger: Boolean) = fragment_enter_pin_finger_button
        .isVisible(useFinger)

    private fun useBiometric() {
        if (!requireActivity().packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            Toast.makeText(
                context, getString(R.string.finger_not_supported),
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        activity?.let {
            val executor = MainThreadExec()
            val biometricPrompt = BiometricPrompt(it, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    viewModel.showMainScreen()
                }
            })

            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.finger_authorization))
                .setSubtitle(getString(R.string.enter_pin_fragment_use_fingerprint_for_auth))
                .setNegativeButtonText(getString(R.string.nter_pin_fragment_cancel))
                .build()

            biometricPrompt.authenticate(promptInfo)
        }

    }
}


