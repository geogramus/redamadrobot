package ru.geogram.redmadrobottimetracker.app.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.redmadrobot.lib.sd.LoadingStateDelegate
import ru.geogram.redmadrobottimetracker.app.R
import ru.geogram.redmadrobottimetracker.app.di.DI
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.MainActivityViewModel
import ru.geogram.redmadrobottimetracker.app.utils.getViewModel
import ru.geogram.redmadrobottimetracker.app.utils.viewModelFactory
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import javax.inject.Inject


class MainActivity: AppCompatActivity() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var screenState: LoadingStateDelegate

    private val navigator = object : SupportAppNavigator(this, R.id.mainContainer) {
        override fun applyCommands(commands: Array<Command>) {
            super.applyCommands(commands)
            supportFragmentManager.executePendingTransactions()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DI.app.inject(this)
        val viewModelFactory = viewModelFactory { DI.AUTH.get().mainViewModel() }

        viewModel = getViewModel(viewModelFactory)
    }


    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

}
