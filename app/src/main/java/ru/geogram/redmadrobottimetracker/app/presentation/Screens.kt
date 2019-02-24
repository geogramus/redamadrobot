package ru.geogram.redmadrobottimetracker.app.presentation

import ru.geogram.redmadrobottimetracker.app.presentation.fragment.FragmentAuthorization
import ru.geogram.redmadrobottimetracker.app.presentation.fragment.MainScreen
import ru.geogram.redmadrobottimetracker.app.presentation.fragment.UserFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {
    object ShowUserFragment : SupportAppScreen() {
        override fun getFragment() = UserFragment()
    }

    object ShowAuthFragment : SupportAppScreen() {
        override fun getFragment() = FragmentAuthorization()
    }

    object ShowMainScreenFragment : SupportAppScreen() {
        override fun getFragment() = MainScreen()
    }
}