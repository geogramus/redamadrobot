package ru.geogram.redmadrobottimetracker.app.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.fragment_create_pin.*
import ru.geogram.redmadrobottimetracker.app.R
import ru.geogram.redmadrobottimetracker.app.di.DI
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.CreatePinViewModel
import ru.geogram.redmadrobottimetracker.app.utils.getViewModel
import ru.geogram.redmadrobottimetracker.app.utils.isEnable
import ru.geogram.redmadrobottimetracker.app.utils.observe
import ru.geogram.redmadrobottimetracker.app.utils.viewModelFactory

class CreatePinFragment : BaseKeyboardFragment() {

    companion object {
        fun getInstance(): CreatePinFragment = CreatePinFragment()
    }

    private lateinit var viewModel: CreatePinViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val viewModelFactory = viewModelFactory { DI.AUTH.get().createPinViewModel() }
        viewModel = getViewModel(viewModelFactory)
        return inflater.inflate(R.layout.fragment_create_pin, container, false)
    }

    private var pin = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNumbersListeners()
        create_pin_layout_et_pin.textChanges()
                .subscribe {
                    pin= it.toString()
                    viewModel.isPinValid(it.toString())
                }.disposeOnDetach()
        fragment_create_pin_remove_btn.clicks()
                .subscribe {
                    removeLastSimbol()
                }.disposeOnDetach()
        fragment_create_pin_save_btn.clicks()
                .subscribe {
                    viewModel.saveUseFingerSetting(create_pin_layout_finger_switch.isChecked)
                    viewModel.savePin(pin)
                    viewModel.showMainScreen()
                }.disposeOnDetach()
        observe(viewModel.isValidPin, this::onPinChanged)
    }

    override fun pinStringChanged(pin: String) {
        create_pin_layout_et_pin.setText(pin, TextView.BufferType.EDITABLE)
    }

    private fun onPinChanged(pinIsCorrect: Boolean) = fragment_create_pin_save_btn.isEnable(pinIsCorrect)
}