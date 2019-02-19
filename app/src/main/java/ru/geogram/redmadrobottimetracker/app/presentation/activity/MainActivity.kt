package ru.geogram.redmadrobottimetracker.app.presentation.activity

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import ru.geogram.domain.model.auth.UserInfo
import ru.geogram.redmadrobottimetracker.app.R
import ru.geogram.redmadrobottimetracker.app.di.DI
import ru.geogram.redmadrobottimetracker.app.presentation.fragment.FragmentAuthorization
import ru.geogram.redmadrobottimetracker.app.presentation.fragment.UserFragment
import ru.geogram.redmadrobottimetracker.app.presentation.presenter.AuthoriztionViewModel
import ru.geogram.redmadrobottimetracker.app.utils.getViewModel
import ru.geogram.redmadrobottimetracker.app.utils.viewModelFactory

class MainActivity : AppCompatActivity(), FragmentAuthorization.FragmentAuthorizationInterface {
    override fun showUserFragment(userInfo: UserInfo) {
        showFragment(UserFragment(userInfo))
    }

    private lateinit var viewModel: AuthoriztionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showFragment(FragmentAuthorization())
        val viewModelFactory = viewModelFactory { DI.user.get().userViewModel() }

    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            if (!fragment.isAdded) {
                add(R.id.mainContainer, fragment)
            } else {
                replace(R.id.mainContainer, fragment)
            }
            addToBackStack(null)
            commit()
        }
        window.statusBarColor = ContextCompat.getColor(this, R.color.status_bar_grey)
    }
}
