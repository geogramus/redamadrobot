package ru.geogram.data.model.converter


import ru.geogram.data.model.db.user.UserEntity
import ru.geogram.data.model.network.user.LoginModel
import ru.geogram.data.model.network.user.LoginResponseModel
import ru.geogram.domain.model.auth.AuthInfo
import ru.geogram.domain.model.auth.ErrorInfo
import ru.geogram.domain.model.auth.LoginPassword
import ru.geogram.domain.model.auth.UserInfo

object AuthConverter {

    fun convertToLoginModel(source: LoginPassword): LoginModel {
        return LoginModel(
                source.login,
                source.password
        )
    }

    fun checkConverter(): AuthInfo {
        return AuthInfo(

        )
    }

    fun fromNetwork(loginResponse: LoginResponseModel): AuthInfo {
        val user = loginResponse.data?.user
        val error = loginResponse.error
        return AuthInfo(
            user?.let {
                UserInfo(
                    id = it.id,
                    first_name = it.first_name,
                    last_name = it.last_name,
                    role = it.role,
                    email = it.email,
                    is_staff = it.is_staff
                )
            } ?: {
                null
            }(),
            error?.let {
                ErrorInfo(it.code, error.description)
            } ?: {
                null
            }()
        )
    }

    fun toDatabase(loginResponse: AuthInfo): UserEntity {
        val user = loginResponse.userInfo
        return UserEntity(
            first_name = user?.first_name,
            role = user?.role,
            last_name = user?.last_name,
            email = user?.email,
            id = user?.id,
            stuff = user?.is_staff
        )
    }

    fun fromDatabase(source: UserEntity): AuthInfo {
        return AuthInfo(
            UserInfo(
                first_name = source.first_name,
                role = source.role,
                last_name = source.last_name,
                email = source.email,
                id = source.id,
                is_staff = source.stuff
            ),
            null
        )
    }

}