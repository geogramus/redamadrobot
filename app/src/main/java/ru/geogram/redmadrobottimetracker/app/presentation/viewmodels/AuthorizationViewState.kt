package ru.geogram.redmadrobottimetracker.app.presentation.viewmodels

import ru.geogram.domain.model.auth.AuthInfo


sealed class UserViewState
data class Data(val user: AuthInfo? = null) : UserViewState()
object Loading : UserViewState()
data class Error(val th: Throwable, val user: AuthInfo? = null) : UserViewState()