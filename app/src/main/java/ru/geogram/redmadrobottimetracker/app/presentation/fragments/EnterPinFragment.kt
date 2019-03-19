package ru.geogram.redmadrobottimetracker.app.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.fragment_enter_pin.*
import ru.geogram.redmadrobottimetracker.app.R
import ru.geogram.redmadrobottimetracker.app.di.DI
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.EnterPinViewModel
import ru.geogram.redmadrobottimetracker.app.utils.*

class EnterPinFragment : BaseKeyboardFragment(){
//    , FingerPrintAuthCallback {

    companion object {
        fun getInstance(): EnterPinFragment = EnterPinFragment()
    }

    private lateinit var viewModel: EnterPinViewModel
//    private val finger by lazy {
//        context?.let {
//            FingerPrintAuthHelper.getHelper(it, this);
//        }
//    }

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
        observe(viewModel.isValidPin, this::onPinChanged)
        observe(viewModel.usingFingerScan, this::onUseFingerChanged)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun pinStringChanged(pin: String) = fragment_enter_pin_et
        .setText(pin, TextView.BufferType.EDITABLE)

//    override fun onNoFingerPrintHardwareFound() {
//        fragment_enter_pin_help_tv.text = getString(R.string.enter_pin_fragment_enter_password)
//    }
//
//    override fun onAuthFailed(errorCode: Int, errorMessage: String?) {
//        fragment_enter_pin_help_tv.text = getString(R.string.enter_pin_fragment_enter_password)
//    }
//
//    override fun onNoFingerPrintRegistered() {
//        fragment_enter_pin_help_tv.text = getString(R.string.enter_pin_fragment_enter_password)
//    }
//
//    override fun onBelowMarshmallow() {
//        fragment_enter_pin_help_tv.text = getString(R.string.enter_pin_fragment_enter_password)
//    }
//
//    override fun onAuthSuccess(cryptoObject: FingerprintManager.CryptoObject?) {
//        viewModel.showMainScreen()
//    }

    private fun onPinChanged(pinIsCorrect: Boolean) {
        if (pinIsCorrect) viewModel.showMainScreen() else
            fragment_enter_pin_incorrect_pin_tv.Visible()
    }

    private fun onUseFingerChanged(useFinger: Boolean) {
//        if (useFinger) finger?.startAuth()
    }

    override fun onPause() {
        super.onPause()
//        finger?.stopAuth()
    }
}


