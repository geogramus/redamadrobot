package ru.geogram.redmadrobottimetracker.app.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.redmadrobot.lib.sd.LoadingStateDelegate
import kotlinx.android.synthetic.main.fragment_authorization.*
import ru.geogram.redmadrobottimetracker.app.R
import ru.geogram.redmadrobottimetracker.app.di.DI
import ru.geogram.redmadrobottimetracker.app.presentation.fragment.FragmentAuthorization
import ru.geogram.redmadrobottimetracker.app.presentation.fragment.UserFragment
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.*
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ViewState
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Data
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.ErrorViewState
import ru.geogram.redmadrobottimetracker.app.presentation.viewstates.Loading
import ru.geogram.redmadrobottimetracker.app.utils.getViewModel
import ru.geogram.redmadrobottimetracker.app.utils.observe
import ru.geogram.redmadrobottimetracker.app.utils.viewModelFactory
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import javax.inject.Inject




class MainActivity: AppCompatActivity(), FragmentAuthorization.FragmentAuthorizationInterface {
    override fun showUserFragment() {
        showFragment(UserFragment())
    }
    @Inject
    lateinit var navigatorHolder: NavigatorHolder
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var screenState: LoadingStateDelegate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DI.app.inject(this)
        screenState = LoadingStateDelegate(fragment_authorization_content, fragment_authorization_progress_bar)
        val viewModelFactory = viewModelFactory { DI.AUTH.get().mainViewModel() }

        viewModel = getViewModel(viewModelFactory)
        observe(viewModel.check, this::onUserChanged)
    }

    private fun onUserChanged(viewState: ViewState) {
        when (viewState) {
            is Data -> {
                showUserFragment()
            }
            is Loading -> {
                screenState.showLoading()
            }
            is ErrorViewState -> {
                showFragment(FragmentAuthorization())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainContainer, fragment)
            commit()
        }
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar_grey)
    }

    private val navigator = object : SupportAppNavigator(this, R.id.mainContainer) {
        override fun applyCommands(commands: Array<Command>) {
            super.applyCommands(commands)
            supportFragmentManager.executePendingTransactions()
        }
    }
}
