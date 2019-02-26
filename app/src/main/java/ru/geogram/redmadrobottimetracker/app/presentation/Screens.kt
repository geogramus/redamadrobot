package ru.geogram.redmadrobottimetracker.app.presentation

import ru.geogram.redmadrobottimetracker.app.presentation.fragments.AuthorizationFragment
import ru.geogram.redmadrobottimetracker.app.presentation.fragments.MainScreenFragment
import ru.geogram.redmadrobottimetracker.app.presentation.fragments.ProjectsFragment
import ru.geogram.redmadrobottimetracker.app.presentation.fragments.UserFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {
    object ShowUserFragment : SupportAppScreen() {
        override fun getFragment() = UserFragment()
    }

    object ShowAuthFragment : SupportAppScreen() {
        override fun getFragment() = AuthorizationFragment()
    }

    object ShowMainScreenFragment : SupportAppScreen() {
        override fun getFragment() = MainScreenFragment()
    }

    object ShowProjectsFragment : SupportAppScreen() {
        override fun getFragment() = ProjectsFragment()
    }
}