package ru.geogram.redmadrobottimetracker.app.presentation.fragments

import android.widget.Button
import kotlinx.android.synthetic.main.keyboard.*

abstract class BaseKeyboardFragment : BaseFragment() {

    protected var pinString = ""
    private val removeSimbolLength = 1
    private val pinStringLength = 4
    private val minSimbolsLength = 0

    protected fun setNumbersListeners() {
        one_number_btn.setOnClickListener {
            onButtonClick(one_number_btn)
        }
        two_number_btn.setOnClickListener {
            onButtonClick(two_number_btn)
        }
        three_number_btn.setOnClickListener {
            onButtonClick(three_number_btn)
        }
        four_number_btn.setOnClickListener {
            onButtonClick(four_number_btn)
        }
        five_number_btn.setOnClickListener {
            onButtonClick(five_number_btn)
        }
        six_number_btn.setOnClickListener {
            onButtonClick(six_number_btn)
        }
        seven_number_btn.setOnClickListener {
            onButtonClick(seven_number_btn)
        }
        eight_number_btn.setOnClickListener {
            onButtonClick(eight_number_btn)
        }
        nine_number_btn.setOnClickListener {
            onButtonClick(nine_number_btn)
        }
        zero_number_btn.setOnClickListener {
            onButtonClick(zero_number_btn)
        }
    }

    abstract fun pinStringChanged(pin: String)

    protected fun removeLastSimbol() {
        if (pinString.length != minSimbolsLength) {
            pinString = pinString.take(pinString.length - removeSimbolLength)
        }
        pinStringChanged(pinString)
    }

    private fun onButtonClick(button: Button) {
        if (pinString.length <= pinStringLength) {
            pinString += button.text
        }
        pinStringChanged(pinString)
    }
}