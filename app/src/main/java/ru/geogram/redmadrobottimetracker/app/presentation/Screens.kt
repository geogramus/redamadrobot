package ru.geogram.redmadrobottimetracker.app.presentation

import ru.geogram.redmadrobottimetracker.app.presentation.fragments.AuthorizationFragment
import ru.geogram.redmadrobottimetracker.app.presentation.fragments.MainScreenFragment
import ru.geogram.redmadrobottimetracker.app.presentation.fragments.ProjectsFragment
import ru.geogram.redmadrobottimetracker.app.presentation.fragments.UserFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object ShowUserFragment : SupportAppScreen() {
    override fun getFragment() = UserFragment.getInstance()
}

object ShowAuthFragment : SupportAppScreen() {
    override fun getFragment() = AuthorizationFragment.getInstance()
}

object ShowMainScreenFragment : SupportAppScreen() {
    override fun getFragment() = MainScreenFragment.getInstance()
}

class ShowProjectsFragment(val date: String) : SupportAppScreen() {
    override fun getFragment() = ProjectsFragment.getInstance(date)
}