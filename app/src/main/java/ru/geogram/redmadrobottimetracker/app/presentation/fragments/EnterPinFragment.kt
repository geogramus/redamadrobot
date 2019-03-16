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
import ru.geogram.redmadrobottimetracker.app.utils.getViewModel
import ru.geogram.redmadrobottimetracker.app.utils.observe
import ru.geogram.redmadrobottimetracker.app.utils.viewModelFactory

class EnterPinFragment : BaseKeyboardFragment() {

    companion object {
        fun getInstance(): EnterPinFragment = EnterPinFragment()
    }

    private lateinit var viewModel: EnterPinViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val viewModelFactory = viewModelFactory { DI.AUTH.get().createEnterPinViewModel() }
        viewModel = getViewModel(viewModelFactory)
        return inflater.inflate(R.layout.fragment_enter_pin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragment_enter_pin_et.textChanges()
            .subscribe {
                viewModel.isPinValid(it.toString())
            }.disposeOnDetach()
        fragment_enter_pin_remove_btn.clicks()
            .subscribe {
                removeLastSimbol()
            }.disposeOnDetach()
        observe(viewModel.isValidPin, this::onPinChanged)
    }

    override fun pinStringChanged(pin: String) = fragment_enter_pin_et.setText(pin, TextView.BufferType.EDITABLE)


    private fun onPinChanged(pinIsCorrect: Boolean) = viewModel.showMainScreen()
}