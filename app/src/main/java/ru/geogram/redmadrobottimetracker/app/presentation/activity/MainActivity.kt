package ru.geogram.redmadrobottimetracker.app.presentation.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.redmadrobot.lib.sd.LoadingStateDelegate
import kotlinx.android.synthetic.main.fragment_authorization.*
import ru.geogram.redmadrobottimetracker.app.R
import ru.geogram.redmadrobottimetracker.app.di.DI
import ru.geogram.redmadrobottimetracker.app.presentation.fragment.FragmentAuthorization
import ru.geogram.redmadrobottimetracker.app.presentation.fragment.UserFragment
import ru.geogram.redmadrobottimetracker.app.presentation.viewmodels.*
import ru.geogram.redmadrobottimetracker.app.utils.getViewModel
import ru.geogram.redmadrobottimetracker.app.utils.observe
import ru.geogram.redmadrobottimetracker.app.utils.viewModelFactory

class MainActivity : AppCompatActivity(), FragmentAuthorization.FragmentAuthorizationInterface {
    override fun showUserFragment() {
        showFragment(UserFragment())
    }

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var screenState: LoadingStateDelegate
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        screenState = LoadingStateDelegate(fragment_authorization_content, fragment_authorization_progress_bar)
        val viewModelFactory = viewModelFactory { DI.user.get().mainViewModel() }
        viewModel = getViewModel(viewModelFactory)
        observe(viewModel.authCheck, this::onUserChanged)
    }

    private fun onUserChanged(viewState: UserViewState) {
        when (viewState) {
            is Data -> {
                showUserFragment()
            }
            is Loading -> {
                screenState.showLoading()
            }
            is Error -> {
                showFragment(FragmentAuthorization())
            }
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainContainer, fragment)
            commit()
        }
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar_grey)
    }
}
