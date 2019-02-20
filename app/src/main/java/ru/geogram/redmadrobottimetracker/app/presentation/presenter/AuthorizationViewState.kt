package ru.geogram.redmadrobottimetracker.app.presentation.presenter

import ru.geogram.domain.model.auth.AuthInfo
import ru.geogram.domain.model.auth.UserInfo


sealed class UserViewState
data class Data(val user: AuthInfo?) : UserViewState()
object Loading : UserViewState()
data class Error(val th: Throwable) : UserViewState()