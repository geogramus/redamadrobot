package ru.geogram.redmadrobottimetracker.app.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.geogram.redmadrobottimetracker.app.R
import ru.geogram.redmadrobottimetracker.app.di.DI
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.MainActivityViewModel
import ru.geogram.redmadrobottimetracker.app.utils.getViewModel
import ru.geogram.redmadrobottimetracker.app.utils.viewModelFactory
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command


class MainActivity: AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    private val navigator = object : SupportAppNavigator(this, R.id.mainContainer) {
        override fun applyCommands(commands: Array<Command>) {
            super.applyCommands(commands)
            supportFragmentManager.executePendingTransactions()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModelFactory = viewModelFactory { DI.AUTH.get().mainViewModel() }
        viewModel = getViewModel(viewModelFactory)
    }


    override fun onResume() {
        super.onResume()
        viewModel.setNavigator(navigator)
    }

    override fun onPause() {
        viewModel.removeNavigator()
        super.onPause()
    }

}
