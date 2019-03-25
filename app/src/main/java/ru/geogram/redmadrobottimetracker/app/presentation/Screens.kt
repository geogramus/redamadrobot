package ru.geogram.redmadrobottimetracker.app.presentation

import ru.geogram.redmadrobottimetracker.app.presentation.fragments.*
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

class ShowProjectsFragment(val bundle: String) : SupportAppScreen() {
    override fun getFragment(): ProjectsFragment {
        return ProjectsFragment.getInstance(bundle)
    }
}

object ShowCreatePinFragment : SupportAppScreen() {
    override fun getFragment() = CreatePinFragment.getInstance()
}

object ShowEnterPinFragment : SupportAppScreen() {
    override fun getFragment() = EnterPinFragment.getInstance()
}


object ShowRegistrationFragment : SupportAppScreen() {
    override fun getFragment() = RegistrationFragment.getInstance()
}